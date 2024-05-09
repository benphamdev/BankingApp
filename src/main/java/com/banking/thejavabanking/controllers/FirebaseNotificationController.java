package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.NotificationMessageDTO;
import com.banking.thejavabanking.services.impl.FirebaseMessagingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push-notification")
@RequiredArgsConstructor
public class FirebaseNotificationController {
    private final FirebaseMessagingServiceImpl firebaseMessagingService;

    @PostMapping
    public String sendNotification(@RequestBody NotificationMessageDTO notificationMessageDTO) {
        return firebaseMessagingService.sendNotification(notificationMessageDTO);
    }
}
