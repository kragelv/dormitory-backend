package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.request.LoginUserRequestDto;
import by.bsuir.dorm.dto.request.RefreshTokenRequestDto;
import by.bsuir.dorm.dto.response.AccessResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AccessResponseDto login(LoginUserRequestDto dto, HttpServletRequest request, HttpServletResponse response);

    AccessResponseDto refresh(RefreshTokenRequestDto dto, HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
