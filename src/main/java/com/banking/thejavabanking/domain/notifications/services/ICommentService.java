package com.banking.thejavabanking.domain.notifications.services;

import com.banking.thejavabanking.domain.notifications.entites.Comment;

import java.util.List;

public interface ICommentService {
    Comment createComment(Long postId, String postBy, String content);

    List<Comment> getCommentsByPostId(Long postId);
}
