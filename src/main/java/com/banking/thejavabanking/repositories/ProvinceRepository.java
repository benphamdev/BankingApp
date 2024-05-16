package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.Enums;
import com.banking.thejavabanking.models.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    Boolean existsByName(Enums.Provinces name);

    Optional<Province> findByName(Enums.Provinces name);
}
