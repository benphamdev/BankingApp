package com.banking.thejavabanking.domain.accounts.repositories;

import com.banking.thejavabanking.domain.accounts.entity.Province;
import com.banking.thejavabanking.domain.common.constants.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    Boolean existsByName(Enums.Provinces name);

    Optional<Province> findByName(Enums.Provinces name);
}
