package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.banking.thejavabanking.models.entity.Comment;
import com.banking.thejavabanking.models.entity.Post;
import com.banking.thejavabanking.repositories.CommentRepository;
import com.banking.thejavabanking.repositories.PostRepository;
import com.banking.thejavabanking.services.ICommentService;
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
            throw new AppException(ErrorResponse.POST_NOT_FOUND);

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
