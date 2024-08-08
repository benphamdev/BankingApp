package com.banking.thejavabanking.domain.notifications.services;

import com.banking.thejavabanking.contract.abstractions.shared.response.PageResponse;
import com.banking.thejavabanking.domain.notifications.dto.requests.PostCreationRequest;
import com.banking.thejavabanking.domain.notifications.dto.responses.PostResponse;
import com.banking.thejavabanking.domain.notifications.entites.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    Post savePost(PostCreationRequest post, MultipartFile multipartFile) throws IOException;

    List<PostResponse> getPosts();

    Post getPostById(Long id);

    void likePost(Long id);

    List<Post> searchPosts(String name);

    void updatePost(Integer id);

    PageResponse<?> getAllPostWithSortBy(int pageNo, int pageSize);

    PageResponse<?> getAllPostWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllPostWithMultiplyColumn(int pageNo, int pageSize, String... sorts);

    PageResponse<?> getAllPostWithPagingAndSorting(
            int pageNo, int pageSize, String search, String sortBy
    );
}
