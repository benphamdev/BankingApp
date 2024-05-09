package com.banking.thejavabanking.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_comment")
public class Comment extends BaseEntity {
    String content;
    String author;

    @ManyToOne
    @JoinColumn(
            name = "post_id", nullable = false
    )
    Post post;
}
