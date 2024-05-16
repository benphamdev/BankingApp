package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.NotificationMessageDTO;
import com.banking.thejavabanking.dto.requests.PostCreationRequest;
import com.banking.thejavabanking.dto.respones.PageResponse;
import com.banking.thejavabanking.dto.respones.PostResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.banking.thejavabanking.mapper.PostMapper;
import com.banking.thejavabanking.models.entity.Photo;
import com.banking.thejavabanking.models.entity.Post;
import com.banking.thejavabanking.models.entity.Tag;
import com.banking.thejavabanking.repositories.PhoneTokenRepository;
import com.banking.thejavabanking.repositories.PhotoRepository;
import com.banking.thejavabanking.repositories.PostRepository;
import com.banking.thejavabanking.repositories.search.SearchPostRepository;
import com.banking.thejavabanking.services.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.banking.thejavabanking.exceptions.ErrorResponse.POST_NOT_FOUND;
import static com.banking.thejavabanking.utils.AppConst.SORT_BY;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PostServiceImpl implements IPostService {
    PostRepository postRepository;
    PhotoServiceImpl photoService;
    PhotoRepository photoRepository;
    TagServiceImpl tagService;
    PostMapper postMapper;
    SearchPostRepository searchPostRepository;
    FirebaseMessagingServiceImpl firebaseMessagingService;
    PhoneTokenRepository phoneTokenRepository;

    @Override
    public Post savePost(PostCreationRequest post, MultipartFile multipartFile) throws IOException {
        Photo photo;
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
        } else {photo = null;}
        List<Tag> tags = tagService.getTagsByIds(post.getListTagId());

        Post post1 = Post.builder()
                         .name(post.getName())
                         .content(post.getContent())
                         .likeCount(0L)
                         .viewCount(0L)
                         .photo(photo)
                         .tags(tags)
                         .build();

        // send notification to all users
        phoneTokenRepository.findAll().forEach(phoneToken -> {
            firebaseMessagingService.sendNotification(NotificationMessageDTO.builder()
                                                                            .recipientToken(phoneToken.getToken())
                                                                            .title("New Post")
                                                                            .body("New post has been created")
                                                                            .image(photo.getUrl())
                                                                            .data(Map.of(
                                                                                    "postId", "hello",
                                                                                    "name", post.getName()
                                                                            ))
                                                                            .build());
        });

        return postRepository.save(post1);
    }

    @Override
    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream().map(postMapper::toPostResponse).toList();
    }

    @Override
    public Post getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post p = post.get();
            p.setViewCount(p.getViewCount() + 1);
            return postRepository.save(p);
        }
        throw new AppException(POST_NOT_FOUND);
    }

    @Override
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

    @Override
    public PageResponse<?> getAllPostWithSortBy(int pageNo, int pageSize) {
        if (pageNo > 0) pageNo--;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> pagePosts = postRepository.findAll(pageable);
        return PageResponse.builder()
                           .page(pagePosts.getNumber())
                           .size(pagePosts.getSize())
                           .total((int) pagePosts.getTotalPages())
                           .items(pagePosts.stream().map(postMapper::toPostResponse).toList())
                           .build();
    }

    @Override
    public PageResponse<?> getAllPostWithSortBy(int pageNo, int pageSize, String sortBy) {
        if (pageNo > 0) pageNo--;
        List<Sort.Order> sorts = new ArrayList<>();
        if (StringUtils.hasLength(sortBy)) {
            // name:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find())
                sorts.add(new Sort.Order(Sort.Direction.fromString(matcher.group(3)), matcher.group(1)));
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));
        Page<Post> pagePost = postRepository.findAll(pageable);
        return PageResponse.builder()
                           .page(pagePost.getNumber())
                           .size(pagePost.getSize())
                           .total((int) pagePost.getTotalElements())
                           .items(pagePost.stream().map(postMapper::toPostResponse).toList())
                           .build();
    }

    @Override
    public PageResponse<?> getAllPostWithMultiplyColumn(int pageNo, int pageSize, String... sorts) {
        if (pageNo > 0) pageNo--;

        List<Sort.Order> orders = new ArrayList<>();

        for (String sortBy : sorts) {
            if (StringUtils.hasLength(sortBy)) {
                Pattern pattern = Pattern.compile(SORT_BY);
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find())
                    orders.add(new Sort.Order(Sort.Direction.fromString(matcher.group(3)), matcher.group(1)));
            }
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));
        Page<Post> postPage = postRepository.findAll(pageable);

        return PageResponse.builder()
                           .page(pageNo)
                           .size(pageSize)
                           .total(postPage.getTotalPages())
                           .items(postPage.stream().map(postMapper::toPostResponse).toList())
                           .build();
    }

    @Override
    public PageResponse<?> getAllPostWithPagingAndSorting(
            int pageNo, int pageSize, String search, String sortBy
    ) {
        return searchPostRepository.getAllPostsWithPagingAndSorting(pageNo, pageSize, search, sortBy);
    }
}
