package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.EmailDetailRequest;
import com.banking.thejavabanking.dto.requests.UserCreationRequest;
import com.banking.thejavabanking.dto.requests.UserUpdateRequest;
import com.banking.thejavabanking.dto.respones.PageResponse;
import com.banking.thejavabanking.dto.respones.UserResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.banking.thejavabanking.mapper.UserMapper;
import com.banking.thejavabanking.models.entity.Photo;
import com.banking.thejavabanking.models.entity.User;
import com.banking.thejavabanking.repositories.PhotoRepository;
import com.banking.thejavabanking.repositories.RoleRepository;
import com.banking.thejavabanking.repositories.SearchRepository;
import com.banking.thejavabanking.repositories.UserRepository;
import com.banking.thejavabanking.services.IEmailService;
import com.banking.thejavabanking.services.IPhotoService;
import com.banking.thejavabanking.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.banking.thejavabanking.utils.AppConst.SORT_BY;

@Service
@FieldDefaults(
        level = lombok.AccessLevel.PRIVATE,
        makeFinal = true
)
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    IEmailService emailService;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    IPhotoService iPhotoService;
    PhotoRepository photoRepository;
    SearchRepository searchRepository;

    @Override
    public UserResponse createUser(UserCreationRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new AppException(ErrorResponse.EMAIL_EXISTS);

        if (userRepository.existsUserByPhoneNumber(userRequest.getPhoneNumber()))
            throw new AppException(ErrorResponse.PHONE_NUMBER_EXISTS);

        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User newUser = userRepository.save(user);

        // Send email notification
        emailService.sendEmail(EmailDetailRequest.builder()
                                                 .recipient(newUser.getEmail())
                                                 .message("Welcome to The Java Banking")
                                                 .subject("Account Creation")
                                                 .build());

        return getUserReponeseByID(newUser.getId());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(
                "User not found"));
    }

    @Override
    public User getUserByAccountNumber(String accountNumber) {
        return userRepository.getUserByPhoneNumber(accountNumber)
                             .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserResponse getUserReponeseByID(Integer id) {
        User user = userRepository.findUserById(id)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));

        return UserResponse.builder()
                           .id(user.getId())
                           .email(user.getEmail())
                           .firstName(user.getFirstName())
                           .lastName(user.getLastName())
                           .otherName(user.getOtherName())
                           .build();
    }

    public User getUserById(int id) {
        return userRepository.findUserById(id)
                             .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));
    }

    @Override
    public UserResponse getMyProfile() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByEmail(name)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UserUpdateRequest request) {
        User user = userRepository.findUserById(id)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));
        userMapper.updateEntity(user, request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));

        return userMapper.toResponse(userRepository.save(user));
    }

    public String getUserName(UserResponse user) {
        return user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName();
    }

    @Override
    public UserResponse updateProfile(int userId, MultipartFile profilePicture) throws IOException {
        BufferedImage bi = ImageIO.read(profilePicture.getInputStream());

        if (bi == null) {
            throw new AppException(ErrorResponse.INVALID_IMAGE);
        }
        User user = userRepository.findUserById(userId)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));

        Map result = iPhotoService.upload(profilePicture);

        Photo photo = Photo.builder()
                           .publicId((String) result.get("public_id"))
                           .url((String) result.get("secure_url"))
                           .build();
        photoRepository.save(photo);

        String oldPhoto = user.getPhoto() != null ? user.getPhoto().getPublicId() : null;

        user.setPhoto(photo);
        userRepository.save(user);

        if (oldPhoto != null) {
            iPhotoService.delete(oldPhoto);
        }

        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    // default pageNo starts from 0
    @Override
    public PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize) {
        if (pageNo > 0) pageNo--;

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<User> pageUser = userRepository.findAll(pageable);

//        return pageUser.stream()
//                       .map(userMapper::toResponse)
//                       .toList();
        return PageResponse.builder()
                           .page(pageUser.getNumber())
                           .size(pageUser.getSize())
                           .total((int) pageUser.getTotalElements())
                           .items(pageUser.stream()
                                          .map(userMapper::toResponse)
                                          .toList())
                           .build();
    }

    @Override
    public PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy) {
        if (pageNo > 0) pageNo--;

        List<Sort.Order> sorts = new ArrayList<>();
        if (StringUtils.hasLength(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String field = matcher.group(1);
                String order = matcher.group(3);
                sorts.add(new Sort.Order(Sort.Direction.fromString(order), field));
            }
        }

//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));
        Page<User> pageUser = userRepository.findAll(pageable);
//        return pageUser.stream()
//                       .map(userMapper::toResponse)
//                       .toList();
        return PageResponse.builder()
                           .page(pageUser.getNumber())
                           .size(pageUser.getSize())
                           .total((int) pageUser.getTotalElements())
                           .items(pageUser.stream()
                                          .map(userMapper::toResponse)
                                          .toList())
                           .build();
    }

    @Override
    public PageResponse<?> getAllUsersWithSortByMultiColumns(
            int pageNo, int pageSize, String... sorts
    ) {
        if (pageNo > 0) pageNo--;

        List<Sort.Order> orders = new ArrayList<>();

        for (String sortBy : sorts) {
            if (StringUtils.hasLength(sortBy)) {
                // firstName:asc|desc
                Pattern pattern = Pattern.compile(SORT_BY);
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find()) {
                    String field = matcher.group(1);
                    String order = matcher.group(3);
                    orders.add(new Sort.Order(Sort.Direction.fromString(order), field));
                }
            }
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));
        Page<User> pageUser = userRepository.findAll(pageable);
//        return pageUser.stream()
//                       .map(userMapper::toResponse)
//                       .toList();
        return PageResponse.builder()
                           .page(pageNo)
                           .size(pageSize)
                           .total(pageUser.getTotalPages())
                           .items(pageUser.stream()
                                          .map(userMapper::toResponse)
                                          .toList())
                           .build();
    }

    @Override
    public PageResponse<?> getAllUsersWithPagingAndSorting(
            int pageNo, int pageSize, String search, String sortBy
    ) {
        return searchRepository.getAllUsersWithPagingAndSorting(pageNo, pageSize, search, sortBy);
    }

    @Override
    public PageResponse<?> advancedSearchWithCriteria(
            int pageNo, int pageSize, String sortBy, String address, String... search
    ) {
        return searchRepository.advancedSearchUser(pageNo, pageSize, sortBy, address, search);
    }

    @Override
    public PageResponse<?> advancedSearchWithSpecification(
            Pageable pageable, String[] users, String[] search
    ) {
        return null;
    }
}