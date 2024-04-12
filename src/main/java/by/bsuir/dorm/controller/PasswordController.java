package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.request.PasswordChangeRequestDto;
import by.bsuir.dorm.dto.request.PasswordSendRequestDto;
import by.bsuir.dorm.service.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("/send")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendToken(@Valid @RequestBody PasswordSendRequestDto dto){
        passwordService.sendToken(dto);
    }

    @PostMapping("/change")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Valid @RequestBody PasswordChangeRequestDto dto) {
        passwordService.changePassword(dto);
    }
}