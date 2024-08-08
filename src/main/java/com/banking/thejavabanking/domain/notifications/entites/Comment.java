package com.banking.thejavabanking.domain.notifications.entites;

import com.banking.thejavabanking.contract.abstractions.shared.DateTrackingBase;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends DateTrackingBase<Integer> {
    String content;
    String author;

    @ManyToOne
    @JoinColumn(
            name = "post_id", nullable = false
    )
    Post post;
}
