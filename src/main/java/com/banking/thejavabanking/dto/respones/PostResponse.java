package com.banking.thejavabanking.dto.respones;

import com.banking.thejavabanking.models.entity.Tag;
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
