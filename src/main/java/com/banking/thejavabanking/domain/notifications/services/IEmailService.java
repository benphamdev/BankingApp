package com.banking.thejavabanking.domain.notifications.services;

import com.banking.thejavabanking.domain.notifications.dto.requests.EmailDetailRequest;

public interface IEmailService {
    void sendEmail(EmailDetailRequest emailDetail);

    void sendEmailWithAttachment(EmailDetailRequest emailDetail);
}
