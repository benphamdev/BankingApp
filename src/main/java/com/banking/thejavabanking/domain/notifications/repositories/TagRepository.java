package com.banking.thejavabanking.domain.notifications.repositories;

import com.banking.thejavabanking.domain.notifications.entites.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByName(String name);

    boolean existsById(Integer id);

}
