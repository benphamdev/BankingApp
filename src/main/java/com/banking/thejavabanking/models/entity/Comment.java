package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.abstractions.DateTrackingBase;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_comment")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Comment extends DateTrackingBase<Integer> {
    String content;
    String author;

    @ManyToOne
    @JoinColumn(
            name = "post_id", nullable = false
    )
    Post post;
}
