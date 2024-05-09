package com.banking.thejavabanking.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_forgot_password")
public class ForgotPassword extends BaseEntity implements Serializable {
    @Column(
            name = "otp",
            nullable = false
    )
    Integer otp;

    @Column(
            name = "expiration_time",
            nullable = false
    )
    Date expirationTime;

    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    User user;
}
