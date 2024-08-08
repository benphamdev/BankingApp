package com.banking.thejavabanking.domain.authentications.repositories;

import com.banking.thejavabanking.domain.notifications.entites.PhoneToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneTokenRepository extends JpaRepository<PhoneToken, Integer> {
    PhoneToken findByToken(String token);
}
