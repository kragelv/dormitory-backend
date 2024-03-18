package by.bsuir.dorm.service;

public interface AccessJwtService extends JwtService {
    boolean isRefreshedTokenValid(String jwt);
}
