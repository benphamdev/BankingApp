package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.abstractions.DateTrackingBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "tbl_forgot_password")
public class ForgotPassword extends DateTrackingBase<Integer> implements Serializable {
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
