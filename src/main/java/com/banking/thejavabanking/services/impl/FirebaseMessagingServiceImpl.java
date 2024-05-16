package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.NotificationMessageDTO;
import com.banking.thejavabanking.models.entity.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseMessagingServiceImpl {
    private final FirebaseMessaging firebaseMessaging;
    private final UserServiceImpl userService;

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

    @Scheduled(fixedRate = 500000)
    public void sendNotificationGreetBirthday() {
        List<User> users = userService.getAllUsersWithBirthdayToday();
        log.info("Users with birthday today : {}", users.size());
        users.stream()
             .map(user -> NotificationMessageDTO.builder()
                                                .recipientToken(user.getPhoneToken().getToken())
                                                .title("Banking App - Birthday Greeting!")
                                                .body("Happy Birthday to you!")
                                                .image("https://th.bing.com/th/id/R.ff0a96fe20695382c1fde0911ee01808?rik=zw%2bzl2vOM0VSbQ&pid=ImgRaw&r=0")
                                                .data(Map.of(
                                                        "birthday", user.getDob().toString(),
                                                        "name", user.getFirstName() + " " + user.getLastName()
                                                ))
                                                .build())
             .forEach(this::sendNotification);
    }
}
