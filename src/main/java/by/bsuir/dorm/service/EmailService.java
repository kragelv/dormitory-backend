package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.request.EmailConfirmationRequestDto;
import by.bsuir.dorm.dto.request.EmailSendRequestDto;


public interface EmailService {

    String sendConfirmation(String username, EmailSendRequestDto dto);

    String resendConfirmation(String username);

    void confirmEmail(String username, EmailConfirmationRequestDto dto);

    Boolean isAvailable(String email);
}
