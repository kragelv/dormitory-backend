package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.request.EmailConfirmationRequestDto;
import by.bsuir.dorm.dto.request.EmailSendRequestDto;


public interface EmailService {

    void sendConfirmation(String username, EmailSendRequestDto dto);

    void confirmEmail(String username, EmailConfirmationRequestDto dto);

    Boolean isAvailable(String email);
}
