package com.banking.thejavabanking.domain.loans.entity;

import com.banking.thejavabanking.contract.abstractions.shared.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_loan_payment")
public class LoanPayment extends BaseAuditEntity<Integer> implements Serializable {
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
