package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.request.EmailConfirmationRequestDto;
import by.bsuir.dorm.dto.request.EmailSendRequestDto;
import by.bsuir.dorm.service.EmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/available")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Boolean isAvailable(@Valid @NotBlank @Email @RequestParam("email") String email) {
        return emailService.isAvailable(email);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/send")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String sendConfirmation(@Valid @RequestBody EmailSendRequestDto dto){
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return emailService.sendConfirmation(username, dto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/resend")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String sendConfirmation(){
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return emailService.resendConfirmation(username);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/confirm")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmEmail(@Valid @RequestBody EmailConfirmationRequestDto dto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        emailService.confirmEmail(username, dto);
    }
}
