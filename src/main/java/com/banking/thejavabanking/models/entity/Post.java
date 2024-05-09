package com.banking.thejavabanking.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_post")
public class Post extends BaseEntity {
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
