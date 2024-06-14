package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.abstractions.DateTrackingBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "tbl_saving")
public class Saving extends DateTrackingBase<Integer> implements Serializable {
    @Column(name = "maturation_date")
    LocalDateTime maturationDate;

    @Column(name = "base_amount")
    BigDecimal baseAmount;

    @Column(name = "refund_amount")
    BigDecimal refundAmount;

    @Column(name = "duration")
    int duration;

    @Column(name = "status_refund")
    @Builder.Default
    boolean statusRefund = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    User user;
}
