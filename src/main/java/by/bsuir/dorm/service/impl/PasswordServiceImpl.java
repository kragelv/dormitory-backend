package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.config.properties.AppProperties;
import by.bsuir.dorm.dao.RefreshTokenRepository;
import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dao.UserTokenRepository;
import by.bsuir.dorm.dto.request.PasswordChangeRequestDto;
import by.bsuir.dorm.dto.request.PasswordSendRequestDto;
import by.bsuir.dorm.exception.PasswordResetException;
import by.bsuir.dorm.exception.UserNotFoundException;
import by.bsuir.dorm.model.TokenPurpose;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.model.entity.UserToken;
import by.bsuir.dorm.service.PasswordService;
import by.bsuir.dorm.service.UserTokenService;
import by.bsuir.dorm.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PasswordServiceImpl implements PasswordService {
    private static final int TOKEN_STAMP_BYTES_LENGTH = 32;
    private static final int UUID_BYTES_LENGTH = 16;

    private final UserRepository userRepository;
    private final PasswordEncoder userTokenEncoder;
    private final AppProperties appProperties;
    private final UserTokenRepository userTokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserTokenService userTokenService;

    @Override
    public void sendToken(PasswordSendRequestDto dto) {
        final String email = dto.email();
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("Invalid email: " + email));
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
        log.info("Send password reset for User { id = " + user.getId() +
                ", e-mail = " + user.getEmail() +" } : "  + tokenValue);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(dto.email());
        mailMessage.setSubject("Change password");
        final String confirmUri = UriComponentsBuilder
                .fromHttpUrl(appProperties.getPasswordReset().getResetUrl())
                .queryParam("token", tokenValue)
                .toUriString();
        mailMessage.setText("Hello, " + user.getFullName().getName() + " "
                + user.getFullName().getSurname() +
                ".\r\n\r\nYou can change password by clicking the following link: " + confirmUri);
        CompletableFuture.runAsync(() -> mailSender.send(mailMessage));
        userRepository.save(user);
    }

    private UserToken compactUserToken(User user, byte[] tokenStamp) {
        final String tokenStampString = Base64.getEncoder().encodeToString(tokenStamp);
        final String tokenHash = userTokenEncoder.encode(tokenStampString);
        final UserToken userToken = new UserToken();
        userToken.setPurpose(TokenPurpose.PASSWORD);
        userToken.setUser(user);
        userToken.setTokenHash(tokenHash);
        userToken.setExpirationTime(Instant.now().plus(appProperties.getPasswordReset().getLifetime()));
        return userToken;
    }

    @Override
    public void changePassword(PasswordChangeRequestDto dto) {
        byte[] tokenBytes;
        try {
            tokenBytes = Base64.getUrlDecoder().decode(dto.token());
        } catch (IllegalArgumentException ex) {
            throw new PasswordResetException("Bad reset token. Try confirmation again", ex);
        }
        final ByteBuffer bb = ByteBuffer.wrap(tokenBytes)
                .order(ByteOrder.LITTLE_ENDIAN);
        long low = bb.getLong();
        long high = bb.getLong();
        final UUID tokenId = new UUID(high, low);
        UserToken userToken = userTokenRepository.findByPurposeAndId(TokenPurpose.PASSWORD, tokenId)
                .orElseThrow(() -> new PasswordResetException("Bad reset token. Try change password again"));
        userTokenService.deleteToken(userToken);
        if (Instant.now().isAfter(userToken.getExpirationTime())) {
            throw new PasswordResetException("Reset token expired. Try change password again");
        }
        byte[] tokenStamp = new byte[TOKEN_STAMP_BYTES_LENGTH];
        bb.get(tokenStamp);
        final String tokenStampString = Base64.getEncoder().encodeToString(tokenStamp);
        if (!userTokenEncoder.matches(tokenStampString, userToken.getTokenHash())) {
            throw new PasswordResetException("Bad reset token. Try change password again");
        }
        final User user = userToken.getUser();
        user.setPassword(passwordEncoder.encode(dto.password()));
        if (user.getPasswordNeedReset()) {
            user.setPasswordNeedReset(false);
        }
        userRepository.save(user);
        userTokenRepository.deleteAllByPurposeAndUser(TokenPurpose.PASSWORD, user);
        log.info("Password reset for User { id = " + user.getId() +
                ", e-mail = " + user.getEmail() +" } with token: " + dto.token());
    }
}
