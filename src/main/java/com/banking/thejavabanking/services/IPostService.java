package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.PostCreationRequest;
import com.banking.thejavabanking.models.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    Post savePost(PostCreationRequest post, MultipartFile multipartFile) throws IOException;

    List<Post> getPosts();

    Post getPostById(Long id);

    void likePost(Long id);

    List<Post> searchPosts(String name);

    void updatePost(Integer id);
}
