package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.config.properties.AppProperties;
import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dao.UserTokenRepository;
import by.bsuir.dorm.dto.request.EmailConfirmationRequestDto;
import by.bsuir.dorm.dto.request.EmailSendRequestDto;
import by.bsuir.dorm.exception.EmailConfirmationException;
import by.bsuir.dorm.exception.EmailNotAvailableException;
import by.bsuir.dorm.exception.UserNotFoundException;
import by.bsuir.dorm.model.TokenPurpose;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.model.entity.UserToken;
import by.bsuir.dorm.service.EmailService;
import by.bsuir.dorm.service.UserSecurityService;
import by.bsuir.dorm.service.UserService;
import by.bsuir.dorm.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private static final int TOKEN_STAMP_BYTES_LENGTH = 32;
    private static final int UUID_BYTES_LENGTH = 16;

    private final UserRepository userRepository;
    private final UserSecurityService userSecurityService;
    private final AppProperties appProperties;
    private final UserTokenRepository userTokenRepository;
    private final PasswordEncoder userTokenEncoder;
    private final JavaMailSender mailSender;

    @Override
    public void sendConfirmation(String username, EmailSendRequestDto dto) {
        final String email = dto.email();
        if (userRepository.existsByEmailIgnoreCase(email))
            throw new EmailNotAvailableException("Email '" + email + "' is not available");
        User user = userSecurityService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User { id = " + id  +" } is not found"));
        final SecureRandom random = RandomUtil.getSecureRandom();
        byte[] tokenRandomStamp = new byte[TOKEN_STAMP_BYTES_LENGTH];
        random.nextBytes(tokenRandomStamp);
        final UserToken userToken = compactUserToken(user, tokenRandomStamp);
        userTokenRepository.save(userToken);
        byte[] tokenBytes = new byte[TOKEN_STAMP_BYTES_LENGTH + UUID_BYTES_LENGTH];
        final UUID tokenId = userToken.getId();
        ByteBuffer.wrap(tokenBytes)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putLong(tokenId.getLeastSignificantBits())
                .putLong(tokenId.getMostSignificantBits())
                .put(tokenRandomStamp);
        final String tokenValue = Base64.getUrlEncoder().encodeToString(tokenBytes);
        user.setEmail(email);
        user.setEmailConfirmed(false);
        log.info("Send confirmation email for User { id = " + user.getId() +
                ", e-mail = " + user.getEmail() +" } : "  + tokenValue);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Confirm E-mail");
        final String confirmUri = UriComponentsBuilder
                .fromHttpUrl(appProperties.getEmailConfirmation().getConfirmUrl())
                .queryParam("token", tokenValue)
                .toUriString();
        mailMessage.setText("Hello, " + user.getFullName().getName() + " "
                + user.getFullName().getSurname() +
                ".\r\n\r\nPlease confirm your email address by clicking the following link: " + confirmUri);
        CompletableFuture.runAsync(() -> mailSender.send(mailMessage));
        userRepository.save(user);
    }

    private UserToken compactUserToken(User user, byte[] tokenStamp) {
        final String tokenStampString = Base64.getEncoder().encodeToString(tokenStamp);
        final String tokenHash = userTokenEncoder.encode(tokenStampString);
        final UserToken userToken = new UserToken();
        userToken.setPurpose(TokenPurpose.EMAIL);
        userToken.setUser(user);
        userToken.setTokenHash(tokenHash);
        userToken.setExpirationTime(Instant.now().plus(appProperties.getEmailConfirmation().getLifetime()));
        return userToken;
    }

    @Override
    public void confirmEmail(String username, EmailConfirmationRequestDto dto) {
        byte[] tokenBytes = Base64.getUrlDecoder().decode(dto.token());
        final ByteBuffer bb = ByteBuffer.wrap(tokenBytes)
                .order(ByteOrder.LITTLE_ENDIAN);
        long low = bb.getLong();
        long high = bb.getLong();
        final UUID tokenId = new UUID(high, low);
        User user = userSecurityService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User { id = " + id  +" } is not found"));
        UserToken userToken = userTokenRepository.findByPurposeAndId(TokenPurpose.EMAIL, tokenId)
                .orElseThrow(() -> new EmailConfirmationException("Bad confirmation token. Try confirmation again"));
        if (Instant.now().isAfter(userToken.getExpirationTime())) {
            userTokenRepository.deleteById(tokenId);
            throw new EmailConfirmationException("Confirmation token expired. Try confirmation again");
        }
        byte[] tokenStamp = new byte[TOKEN_STAMP_BYTES_LENGTH];
        bb.get(tokenStamp);
        final String tokenStampString = Base64.getEncoder().encodeToString(tokenStamp);
        if (!userTokenEncoder.matches(tokenStampString, userToken.getTokenHash())) {
            throw new EmailConfirmationException("Bad confirmation token. Try confirmation again");
        }
        user.setEmailConfirmed(true);
        userRepository.save(user);
        userTokenRepository.deleteAllByPurposeAndUser(TokenPurpose.EMAIL, user);
    }

    @Override
    public Boolean isAvailable(EmailSendRequestDto dto) {
        return !userRepository.existsByEmailIgnoreCase(dto.email());
    }
}
