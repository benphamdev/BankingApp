package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.PostCreationRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.models.entity.Post;
import com.banking.thejavabanking.services.impl.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(
            path = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<Post> savePost(
            @RequestPart PostCreationRequest post, @RequestPart(required = false) MultipartFile file
    ) throws IOException {
        return BaseResponse.<Post>builder()
                           .message("create post success")
                           .data(postService.savePost(post, file))
                           .build();
    }

    @GetMapping
    public BaseResponse<List<Post>> getPosts() {
        try {
            return BaseResponse.<List<Post>>builder()
                               .message("get posts success")
                               .data(postService.getPosts())
                               .build();
        } catch (Exception e) {
            return BaseResponse.<List<Post>>builder()
                               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                               .message("get posts failed")
                               .build();

        }
    }

    @GetMapping("/{id}")
    public BaseResponse<Post> getPostById(@PathVariable Long id) {
        try {
            return BaseResponse.<Post>builder()
                               .message("get post success")
                               .data(postService.getPostById(id))
                               .build();
        } catch (EntityNotFoundException e) {
            return BaseResponse.<Post>builder()
                               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                               .message(e.getMessage())
                               .build();
        }
    }

    @GetMapping("/{id}/like")
    public BaseResponse<String> likePost(@PathVariable Long id) {
        try {
            postService.likePost(id);
            return BaseResponse.<String>builder()
                               .status(HttpStatus.OK.value())
                               .message("like post success")
                               .build();
        } catch (EntityNotFoundException e) {
            return BaseResponse.<String>builder()
                               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                               .message(e.getMessage())
                               .build();
        }
    }

    @GetMapping("/search")
    public BaseResponse<List<Post>> searchPosts(@RequestParam String name) {
        try {
            return BaseResponse.<List<Post>>builder()
                               .message("search posts success")
                               .data(postService.searchPosts(name))
                               .build();
        } catch (EntityNotFoundException e) {
            return BaseResponse.<List<Post>>builder()
                               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                               .message(e.getMessage())
                               .build();
        }
    }
}
