package com.banking.thejavabanking.domain.users.repositories;

import com.banking.thejavabanking.domain.users.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.phoneNumber = ?1")
    Boolean existsUserByPhoneNumber(String phone_number);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = ?1")
    Optional<User> getUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserById(int id);

    @Transactional
    @Modifying
    @Query(
            value = "update tbl_user u set u.password = ?2 where u.email = ?1",
            nativeQuery = true
    )
    void updatePassword(String email, String password);

    @Transactional
    @Modifying
    @Query(
            value = "update tbl_user u set u.profile_picture = ?2 where u.email = ?1",
            nativeQuery = true
    )
    void updateUserByPhoto(String email, MultipartFile photo);

    //  month and day of user = month and day of current date
    @Query("SELECT u FROM User u WHERE MONTH(u.dob) = MONTH(CURRENT_DATE) AND DAY(u.dob) = DAY(CURRENT_DATE)")
    List<User> findUserByDob();

}
