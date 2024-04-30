package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.request.PasswordChangeRequestDto;
import by.bsuir.dorm.dto.request.PasswordSendRequestDto;

public interface PasswordService {

    void sendToken(PasswordSendRequestDto dto);

    void changePassword(PasswordChangeRequestDto dto);
}

