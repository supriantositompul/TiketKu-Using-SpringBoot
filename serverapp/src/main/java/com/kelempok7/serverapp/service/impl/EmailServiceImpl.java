package com.kelempok7.serverapp.service.impl;

import java.io.File;
import java.nio.file.FileSystem;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kelempok7.serverapp.models.dto.request.EmailRequest;
import com.kelempok7.serverapp.service.EmailService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    @Override
    public EmailRequest sendRegistrationMessage(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getTo());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getBody());
        mailSender.send(message);
        return emailRequest;
    }

    @Override
    public EmailRequest sendMessageWithAttachment(EmailRequest emailRequest) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("name", emailRequest.getName());

            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());

            String htmlContent = templateEngine.process("welcome-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
         
        } catch (Exception e) {
           log.error("Failed send email by attachment to {}", emailRequest.getTo());
           System.out.println("Error = " + e.getMessage());
        }
        return emailRequest;
    }

}
