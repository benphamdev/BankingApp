package com.banking.thejavabanking.domain.notifications.entites;

import com.banking.thejavabanking.contract.abstractions.shared.BaseAuditEntity;
import com.banking.thejavabanking.domain.users.entities.Photo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends BaseAuditEntity<Integer> {
    String name;
    String content;
    Long likeCount;
    Long viewCount;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    List<Tag> tags = new ArrayList<>();

    @OneToOne(
            targetEntity = Photo.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "photo_id")
    Photo photo;
}
