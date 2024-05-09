package com.banking.thejavabanking.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_loan_payment")
public class LoanPayment extends BaseEntity implements Serializable {
    @Column(name = "pay_amount")
    double payAmount;

    @Column(name = "payment_reference")
    String paymentReference;

    @Column(name = "payment_type")
    String paymentType;

    @ManyToOne
    @JoinColumn(name = "loan_detail_id")
    LoanDetail loanDetail;
}
