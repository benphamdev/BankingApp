package com.banking.thejavabanking.domain.notifications.services;

import com.banking.thejavabanking.domain.notifications.dto.requests.TagRequest;
import com.banking.thejavabanking.domain.notifications.entites.Tag;

import java.util.List;

public interface ITagService {
    Tag saveTag(TagRequest tag);

    Tag getTagById(Integer id);

    boolean existsByTagName(String tagName);

    void deleteTag(Integer id);

    List<Tag> getTags();

    void updateTag(Integer id, TagRequest tag);

    List<Tag> getTagsByIds(List<Integer> ids);
}
