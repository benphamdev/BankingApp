package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.PhoneToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneTokenRepository extends JpaRepository<PhoneToken, Integer> {
    PhoneToken findByToken(String token);
}
