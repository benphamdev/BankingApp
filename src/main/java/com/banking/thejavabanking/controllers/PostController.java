package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.PostCreationRequest;
import com.banking.thejavabanking.dto.respones.PostResponse;
import com.banking.thejavabanking.dto.respones.shared.BaseResponse;
import com.banking.thejavabanking.models.entity.Post;
import com.banking.thejavabanking.services.impl.PostServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostServiceImpl postService;

    @PostMapping(
            path = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
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
    public BaseResponse<List<PostResponse>> getPosts() {
        try {
            return BaseResponse.<List<PostResponse>>builder()
                               .message("get posts success")
                               .data(postService.getPosts())
                               .build();
        } catch (Exception e) {
            return BaseResponse.<List<PostResponse>>builder()
                               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                               .message("get posts failed")
                               .build();

        }
    }

    @GetMapping("/{id}")
    public BaseResponse<Post> getPostById(@PathVariable @Min(1) Long id) {
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
    public BaseResponse<String> likePost(@PathVariable @Min(1) Long id) {
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

    @GetMapping("/list-post")
    public BaseResponse<?> getAllPostBySort(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize
    ) {
        return BaseResponse.builder()
                           .message("get list post with pageNo successfully")
                           .data(postService.getAllPostWithSortBy(pageNo, pageSize))
                           .build();
    }

    @GetMapping("/list-post-sort")
    public BaseResponse<?> getAllPostBySort(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        return BaseResponse.builder()
                           .message("get list post with pageNo successfully")
                           .data(postService.getAllPostWithSortBy(pageNo, pageSize, sortBy))
                           .build();
    }

    @GetMapping("/list-with-sort-by-multiple-columns")
    public BaseResponse<?> getAllPostWithMultiColumns(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String... sorts
    ) {
        return BaseResponse.builder()
                           .status(HttpStatus.OK.value())
                           .message("get post sort with multi columns")
                           .data(postService.getAllPostWithMultiplyColumn(pageNo, pageSize, sorts))
                           .build();
    }

    @GetMapping("/list-post-and-search-with-paging-and-sorting")
    public BaseResponse<?> getAllPostsWithPagingAndSorting(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy
    ) {
        log.info("Request to get post list by pageNo, pageSize and sort by 1 columns");
        return BaseResponse.builder()
                           .status(HttpStatus.OK.value())
                           .message("User list retrieved successfully")
                           .data(postService.getAllPostWithPagingAndSorting(
                                   pageNo,
                                   pageSize,
                                   search,
                                   sortBy
                           ))
                           .build();
    }
}
