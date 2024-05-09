package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.TagRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.models.entity.Tag;
import com.banking.thejavabanking.services.impl.TagServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tag")
@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagServiceImpl tagService;

    @Operation(summary = "Get all tags", description = "Get all tags")
    @GetMapping("/all")
    public BaseResponse<List<Tag>> getAllTags() {
        return BaseResponse.<List<Tag>>builder()
                           .message("List of all tags")
                           .data(tagService.getTags())
                           .build();
    }

    @Operation(summary = "Get tag by id", description = "Get tag by id")
    @GetMapping("/{id}")
    public BaseResponse<Tag> getTagById(@PathVariable Integer id) {
        return BaseResponse.<Tag>builder()
                           .message("Tag with id: " + id)
                           .data(tagService.getTagById(id))
                           .build();
    }

    @Operation(summary = "Add tag", description = "Add tag")
    @PostMapping("/add")
    public BaseResponse<Tag> addTag(@RequestBody TagRequest tag) {
        return BaseResponse.<Tag>builder()
                           .message("Tag added")
                           .data(tagService.saveTag(tag))
                           .build();
    }

    @PutMapping("/{id}")
    public BaseResponse<Tag> updateTag(@PathVariable Integer id, @RequestBody TagRequest tag) {
        tagService.updateTag(id, tag);
        return BaseResponse.<Tag>builder()
                           .message("Tag updated")
                           .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return BaseResponse.<String>builder()
                           .message("Tag deleted")
                           .build();
    }
}
