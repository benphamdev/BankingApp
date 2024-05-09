package com.banking.thejavabanking.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_saving")
public class Saving extends BaseEntity implements Serializable {
    @Column(name = "maturation_date")
    LocalDateTime maturationDate;

    @Column(name = "user_id")
    int userId;

    @Column(name = "base_amount")
    BigDecimal baseAmount;

    @Column(name = "refund_amount")
    BigDecimal refundAmount;

    @Column(name = "duration")
    int duration;

    @Column(name = "status_refund")
    @Builder.Default
    boolean statusRefund = false;
}
