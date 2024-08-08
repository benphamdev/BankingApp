package com.banking.thejavabanking.domain.authentications.repositories;

import com.banking.thejavabanking.domain.authentications.entity.ForgotPassword;
import com.banking.thejavabanking.domain.users.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    @Query("SELECT f FROM ForgotPassword f WHERE f.otp = :otp AND f.user = :user")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

    @Query("DELETE FROM ForgotPassword f WHERE f.id = :id")
    @Transactional
    @Modifying
    void deleteById(Integer id);
}
