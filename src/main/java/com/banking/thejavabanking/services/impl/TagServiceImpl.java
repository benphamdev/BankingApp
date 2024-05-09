package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.TagRequest;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.banking.thejavabanking.models.entity.Tag;
import com.banking.thejavabanking.repositories.TagRepository;
import com.banking.thejavabanking.services.ITagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements ITagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository repository) {this.tagRepository = repository;}

    @Override
    public Tag saveTag(TagRequest tag) {
        if (tagRepository.existsByName(tag.getName()))
            throw new AppException(ErrorResponse.TAG_ALREADY_EXISTS);

        log.info(tag.getName());
        Tag tag1 = Tag.builder()
                      .name(tag.getName())
                      .build();

        return tagRepository.save(tag1);
    }

    @Override
    public Tag getTagById(Integer id) {
        Tag tag = tagRepository.findById(id)
                               .orElseThrow(() -> new AppException(ErrorResponse.TAG_NOT_FOUND));

        return tag;
    }

    @Override
    public boolean existsByTagName(String tagName) {
        return tagRepository.existsByName(tagName);
    }

    @Override
    public void deleteTag(Integer id) {
        if (!tagRepository.existsById(id))
            throw new AppException(ErrorResponse.TAG_NOT_FOUND);
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    @Override
    public void updateTag(Integer id, TagRequest tag) {
        Tag tag1 = tagRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorResponse.TAG_NOT_FOUND));

        tag1.setName(tag.getName());
        tagRepository.save(tag1);
    }

    @Override
    public List<Tag> getTagsByIds(List<Integer> ids) {
        return tagRepository.findAllById(ids);
    }
}
