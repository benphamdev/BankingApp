package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.respones.shared.BaseResponse;
import com.banking.thejavabanking.models.entity.Comment;
import com.banking.thejavabanking.services.impl.CommentServiceImpl;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;

    @GetMapping("/create")
    public BaseResponse<Comment> createComment(
            @RequestParam @Min(1) Long postId,
            @RequestParam String author,
            @RequestParam String content
    ) {
        try {
            return BaseResponse.<Comment>builder()
                               .data(commentService.createComment(postId, author, content))
                               .message("Comment created successfully")
                               .build();
        } catch (Exception e) {
            return BaseResponse.<Comment>builder()
                               .message(e.getMessage())
                               .build();
        }
    }

    @GetMapping("/list-comments/{postId}")
    public BaseResponse<List<Comment>> listComments(@PathVariable Long postId) {
        try {
            return BaseResponse.<List<Comment>>builder()
                               .data(commentService.getCommentsByPostId(postId))
                               .message("List comments successfully")
                               .build();
        } catch (Exception e) {
            return BaseResponse.<List<Comment>>builder()
                               .message(e.getMessage())
                               .build();
        }
    }
}
