package com.banking.thejavabanking.services;

import com.banking.thejavabanking.models.entity.Comment;

import java.util.List;

public interface ICommentService {
    Comment createComment(Long postId, String postBy, String content);

    List<Comment> getCommentsByPostId(Long postId);
}
