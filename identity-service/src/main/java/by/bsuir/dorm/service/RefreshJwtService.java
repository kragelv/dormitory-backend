package by.bsuir.dorm.service;

import by.bsuir.dorm.entity.User;
import by.bsuir.dorm.util.JwtTokenDescriptor;

import java.util.Map;

public interface RefreshJwtService extends JwtService {
    JwtTokenDescriptor generateToken(User user);

    JwtTokenDescriptor generateToken(User user, Map<String, ?> extraClaims);
}
