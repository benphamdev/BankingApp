package com.banking.thejavabanking.domain.notifications.dto.requests;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class NotificationMessageDTO {
    private String recipientToken;
    private String title;
    private String body;
    private String image;
    private Map<String, String> data;
}
