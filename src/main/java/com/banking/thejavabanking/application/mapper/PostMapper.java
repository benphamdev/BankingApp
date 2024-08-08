package com.banking.thejavabanking.application.mapper;

import com.banking.thejavabanking.domain.notifications.dto.responses.PostResponse;
import com.banking.thejavabanking.domain.notifications.entites.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(
            target = "thumbnail",
            source = "post.photo.url"
    )
    PostResponse toPostResponse(Post post);
}
