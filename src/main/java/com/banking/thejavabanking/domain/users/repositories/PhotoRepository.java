package com.banking.thejavabanking.domain.users.repositories;

import com.banking.thejavabanking.domain.users.entities.Photo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Photo p WHERE p.publicId = ?1")
    void deletePhotoByPublicId(String publicId);

}