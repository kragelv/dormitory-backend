package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.UserTokenRepository;
import by.bsuir.dorm.model.entity.UserToken;
import by.bsuir.dorm.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {
    private final UserTokenRepository userTokenRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteToken(UserToken userToken){
        userTokenRepository.delete(userToken);
    }
}
