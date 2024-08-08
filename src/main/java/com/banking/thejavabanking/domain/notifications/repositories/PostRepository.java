package com.banking.thejavabanking.domain.notifications.repositories;

import com.banking.thejavabanking.domain.notifications.entites.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByNameContaining(String name);
}
