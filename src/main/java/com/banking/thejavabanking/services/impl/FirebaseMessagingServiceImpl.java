package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.NotificationMessageDTO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingServiceImpl {
    private final FirebaseMessaging firebaseMessaging;

    public String sendNotification(NotificationMessageDTO notificationMessageDTO) {
        Notification notification = Notification.builder()
                                                .setTitle(notificationMessageDTO.getTitle())
                                                .setBody(notificationMessageDTO.getBody())
                                                .setImage(notificationMessageDTO.getImage())
                                                .build();
        Message message = Message.builder()
                                 .setNotification(notification)
                                 .setToken(notificationMessageDTO.getRecipientToken())
                                 .putAllData(notificationMessageDTO.getData())
                                 .build();

        try {
            firebaseMessaging.send(message);
            return "Notification sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send notification";
        }
    }
}
