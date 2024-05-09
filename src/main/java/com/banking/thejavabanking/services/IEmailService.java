package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.EmailDetailRequest;

public interface IEmailService {
    void sendEmail(EmailDetailRequest emailDetail);

    void sendEmailWithAttachment(EmailDetailRequest emailDetail);
}
