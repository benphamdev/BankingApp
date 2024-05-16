package com.banking.thejavabanking.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
