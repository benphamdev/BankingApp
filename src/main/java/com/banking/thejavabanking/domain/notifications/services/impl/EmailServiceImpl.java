package com.banking.thejavabanking.domain.notifications.services.impl;

import com.banking.thejavabanking.domain.notifications.dto.requests.EmailDetailRequest;
import com.banking.thejavabanking.domain.notifications.services.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class EmailServiceImpl implements IEmailService {
    JavaMailSender javaMailSender;

    @NonFinal
    @Value("${spring.mail.username}")
    String senderEmail;

    @Override
    public void sendEmail(EmailDetailRequest emailDetail) {
        // send email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(emailDetail.getRecipient());
        mailMessage.setSubject(emailDetail.getSubject());
        mailMessage.setText(emailDetail.getMessage());
        javaMailSender.send(mailMessage);
        System.out.println("Email sent successfully");
    }

    @Override
    public void sendEmailWithAttachment(EmailDetailRequest emailDetail) {
        // send email with attachment
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setFrom(senderEmail);
            helper.setTo(emailDetail.getRecipient());
            helper.setSubject(emailDetail.getSubject());
            helper.setText(emailDetail.getMessage());

            FileSystemResource file = new FileSystemResource(new File(emailDetail.getAttachment()));
            helper.addAttachment(file.getFilename(), file);
            javaMailSender.send(mailMessage);

            log.info("Email sent successfully with attachment");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
