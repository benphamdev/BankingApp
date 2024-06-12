package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.UserCreationRequest;
import com.banking.thejavabanking.dto.requests.UserUpdateRequest;
import com.banking.thejavabanking.dto.respones.PageResponse;
import com.banking.thejavabanking.dto.respones.UserResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.banking.thejavabanking.mapper.UserMapper;
import com.banking.thejavabanking.models.entity.Address;
import com.banking.thejavabanking.models.entity.PhoneToken;
import com.banking.thejavabanking.models.entity.Photo;
import com.banking.thejavabanking.models.entity.User;
import com.banking.thejavabanking.repositories.PhoneTokenRepository;
import com.banking.thejavabanking.repositories.PhotoRepository;
import com.banking.thejavabanking.repositories.RoleRepository;
import com.banking.thejavabanking.repositories.UserRepository;
import com.banking.thejavabanking.repositories.search.SearchRepository;
import com.banking.thejavabanking.repositories.specification.UserSpecificationBuilder;
import com.banking.thejavabanking.services.IEmailService;
import com.banking.thejavabanking.services.IPhotoService;
import com.banking.thejavabanking.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.banking.thejavabanking.utils.AppConst.SEARCH_SPEC_OPERATOR;
import static com.banking.thejavabanking.utils.AppConst.SORT_BY;

@Service
@FieldDefaults(
        level = lombok.AccessLevel.PRIVATE,
        makeFinal = true
)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    IEmailService emailService;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    IPhotoService iPhotoService;
    PhotoRepository photoRepository;
    SearchRepository searchRepository;
    PhoneTokenRepository phoneTokenRepository;

    @Override
    public long createUser(UserCreationRequest userRequest) {
//        User user = userMapper.toEntity(userRequest);
//        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User user = User.builder()
                        .firstName(userRequest.getFirstName())
                        .lastName(userRequest.getLastName())
                        .dob(userRequest.getDob())
                        .gender(userRequest.getGender())
                        .phoneNumber(userRequest.getPhoneNumber())
                        .email(userRequest.getEmail())
                        .password(passwordEncoder.encode(userRequest.getPassword()))
                        .build();

        userRequest.getAddresses()
                   .forEach(address -> user.saveAddress(
                           Address.builder()
                                  .apartmentNumber(address.getApartmentNumber())
                                  .floor(address.getFloor())
                                  .building(address.getBuilding())
                                  .streetNumber(address.getStreetNumber())
                                  .street(address.getStreet())
                                  .city(address.getCity())
                                  .country(address.getCountry())
                                  .addressType(address.getAddressType())
                                  .build()));
        // add role default to user

        userRepository.save(user);

        // Send email notification
//        emailService.sendEmail(EmailDetailRequest.builder()
//                                                 .recipient(user.getEmail())
//                                                 .message("Welcome to The Java Banking")
//                                                 .subject("Account Creation")
//                                                 .build());

        return user.getId();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new RuntimeException(
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
                           .build();
    }

    public User getUserById(int id) {
        return userRepository.findUserById(id)
                             .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));
    }

    @Override
    public UserResponse getMyProfile() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication()
                             .getName();
        User user = userRepository.findByEmail(name)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));

        return userMapper.toResponse(user);
    }

    @Override
    public void updateUser(Integer id, UserUpdateRequest request) {
        User user = userRepository.findUserById(id)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));
        userMapper.updateEntity(user, request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));

        userMapper.toResponse(userRepository.save(user));
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
//        User user = userRepository.findUserById(userId)
//                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));

        User user = getUserById(userId);

        Map result = iPhotoService.upload(profilePicture);

        Photo photo = Photo.builder()
                           .publicId((String) result.get("public_id"))
                           .url((String) result.get("secure_url"))
                           .build();
        photoRepository.save(photo);

        String oldPhoto = user.getPhoto() != null ? user.getPhoto()
                                                        .getPublicId() : null;

        user.setPhoto(photo);
        userRepository.save(user);

        if (oldPhoto != null) {
            iPhotoService.delete(oldPhoto);
        }

        return userMapper.toResponse(user);
    }

    @Override
    public void updatePhoneToken(Integer userId, String phoneToken) {
        User user = getUserById(userId);
        PhoneToken phoneToken1 = PhoneToken.builder()
                                           .token(phoneToken)
                                           .build();
        phoneTokenRepository.save(phoneToken1);
        user.setPhoneToken(phoneToken1);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsersWithBirthdayToday() {
        LocalDate currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();
        log.info("Day: {}, Month: {}", day, month);
        return userRepository.findUserByDob();
    }

    // default pageNo starts from 0
    @Override
    public PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize) {
        if (pageNo > 0) pageNo--;

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<User> pageUser = userRepository.findAll(pageable);

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

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));
        Page<User> pageUser = userRepository.findAll(pageable);
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
    public PageResponse<?> getAllUsersWithSortByMultiColumns(int pageNo, int pageSize, String... sorts) {
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
    public PageResponse<?> advancedSearchWithSpecification(Pageable pageable, String[] users, String[] address) {
        Page<User> userList = null;
        if (users != null && address != null) {
            // TODO: Implement search by users and search
            return searchRepository.getUsersJoinedAddress(pageable, users, address);
        } else if (users != null) {
            // TODO: Implement search by users => don't join with other table
//            Specification<User> spec = UserSpec.hasFirstName("p");
//            Specification<User> genderSpec = UserSpec.notEqualGender(Enums.Gender.FEMALE.toString());
//            Specification<User> finalSpec = spec.and(genderSpec);
//            userList = userRepository.findAll(finalSpec, pageable);

            UserSpecificationBuilder userSpecificationBuilder = new UserSpecificationBuilder();
            for (String user : users) {
                Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
                Matcher matcher = pattern.matcher(user);
                if (matcher.find()) {
                    userSpecificationBuilder.with(
                            matcher.group(1),
                            matcher.group(2),
                            matcher.group(3),
                            matcher.group(4),
                            matcher.group(5)
                    );
                }
            }
            userList = userRepository.findAll(userSpecificationBuilder.build(), pageable);
        } else userList = userRepository.findAll(pageable);

        return PageResponse.builder()
                           .page(pageable.getPageNumber())
                           .size(pageable.getPageSize())
                           .total(userList.getTotalPages())
                           .items(userList.stream()
                                          .map(userMapper::toResponse)
                                          .toList())
                           .build();
    }
}