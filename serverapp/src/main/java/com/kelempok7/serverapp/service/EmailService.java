package com.kelempok7.serverapp.service;

import com.kelempok7.serverapp.models.dto.request.EmailRequest;

public interface EmailService {
    EmailRequest sendRegistrationMessage(EmailRequest emailRequest);
    EmailRequest sendMessageWithAttachment(EmailRequest emailRequest);
}
