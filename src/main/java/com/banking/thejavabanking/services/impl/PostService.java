package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.PostCreationRequest;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.banking.thejavabanking.models.entity.Photo;
import com.banking.thejavabanking.models.entity.Post;
import com.banking.thejavabanking.models.entity.Tag;
import com.banking.thejavabanking.repositories.PhotoRepository;
import com.banking.thejavabanking.repositories.PostRepository;
import com.banking.thejavabanking.services.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.banking.thejavabanking.exceptions.ErrorResponse.POST_NOT_FOUND;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PostService implements IPostService {
    PostRepository postRepository;
    PhotoServiceImpl photoService;
    PhotoRepository photoRepository;
    TagServiceImpl tagService;

    public Post savePost(PostCreationRequest post, MultipartFile multipartFile) throws IOException {
        Photo photo = null;
        if (multipartFile != null) {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());

            if (bi == null) {
                throw new AppException(ErrorResponse.INVALID_IMAGE);
            }

            Map result = photoService.upload(multipartFile);

            photo = Photo.builder()
                         .publicId((String) result.get("public_id"))
                         .url((String) result.get("secure_url"))
                         .build();
            photoRepository.save(photo);
        }
        List<Tag> tags = tagService.getTagsByIds(post.getListTagId());

        Post post1 = Post.builder()
                         .name(post.getName())
                         .content(post.getContent())
                         .likeCount(0L)
                         .viewCount(0L)
                         .photo(photo)
                         .tags(tags)
                         .build();
        return postRepository.save(post1);
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post p = post.get();
            p.setViewCount(p.getViewCount() + 1);
            return postRepository.save(p);
        }
        throw new AppException(POST_NOT_FOUND);
    }

    public void likePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post p = post.get();
            p.setLikeCount(p.getLikeCount() + 1);
            postRepository.save(p);
        } else {
            throw new AppException(POST_NOT_FOUND);
        }
    }

    public List<Post> searchPosts(String name) {
        return postRepository.findAllByNameContaining(name);
    }

    @Override
    public void updatePost(Integer id) {

    }
}
