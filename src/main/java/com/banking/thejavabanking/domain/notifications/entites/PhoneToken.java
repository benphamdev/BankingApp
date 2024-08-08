package com.banking.thejavabanking.domain.notifications.entites;

import com.banking.thejavabanking.contract.abstractions.shared.BaseEntity;
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
