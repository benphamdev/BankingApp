package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByName(String name);

    boolean existsById(Integer id);

}
