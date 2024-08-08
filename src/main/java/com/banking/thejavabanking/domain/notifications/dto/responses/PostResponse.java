package com.banking.thejavabanking.domain.notifications.dto.responses;

import com.banking.thejavabanking.domain.notifications.entites.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    Integer id;
    String name;
    String content;
    Long likeCount;
    Long viewCount;
    String thumbnail;
    List<Tag> tags;
}
