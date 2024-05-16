package com.banking.thejavabanking.mapper;

import com.banking.thejavabanking.dto.respones.PostResponse;
import com.banking.thejavabanking.models.entity.Post;
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
