package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.TagRequest;
import com.banking.thejavabanking.models.entity.Tag;

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
