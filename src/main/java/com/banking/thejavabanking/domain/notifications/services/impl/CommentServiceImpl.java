package com.banking.thejavabanking.domain.notifications.services.impl;

import com.banking.thejavabanking.domain.common.constants.EnumsErrorResponse;
import com.banking.thejavabanking.domain.common.exceptions.AppException;
import com.banking.thejavabanking.domain.notifications.entites.Comment;
import com.banking.thejavabanking.domain.notifications.entites.Post;
import com.banking.thejavabanking.domain.notifications.repositories.CommentRepository;
import com.banking.thejavabanking.domain.notifications.repositories.PostRepository;
import com.banking.thejavabanking.domain.notifications.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment createComment(Long postId, String postBy, String content) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty())
            throw new AppException(EnumsErrorResponse.POST_NOT_FOUND);

        Comment comment = Comment.builder()
                                 .post(post.get())
                                 .author(postBy)
                                 .content(content)
                                 .build();

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
