package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.abstractions.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_phone_token")
public class PhoneToken extends BaseEntity<Integer> {
    String token;
}
