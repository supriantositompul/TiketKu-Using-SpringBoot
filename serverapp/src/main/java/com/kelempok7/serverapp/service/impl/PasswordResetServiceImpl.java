package com.kelempok7.serverapp.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.kelempok7.serverapp.models.dto.request.EmailRequest;
import com.kelempok7.serverapp.models.entity.PasswordResetToken;
import com.kelempok7.serverapp.models.entity.User;
import com.kelempok7.serverapp.repository.PasswordResetRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordResetServiceImpl {
    
    private PasswordResetRepository resetRepository;
    private EmailServiceImpl emailService;

    public void createPasswordResetToken(User user, String token){

        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);

        resetRepository.save(myToken);
    }

    public void sendPasswordResetviaEmail(String email, String appUrl, String token){
        String resetPasswordUrl = appUrl + "/resetPassword?token=" + token;
        String subject = "Reset Password";
        String body = "Click Link Below to Reset Password:\n" + resetPasswordUrl;

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(email);
        emailRequest.setSubject(subject);
        emailRequest.setBody(body);

        emailService.sendRegistrationMessage(emailRequest);
    }

    public PasswordResetToken findByToken(String token){
        return resetRepository.findByToken(token);
    }

    public void delete (PasswordResetToken token){
        resetRepository.delete(token);
    }

}
