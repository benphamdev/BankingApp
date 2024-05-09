package com.banking.thejavabanking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = "tbl_loan_disbursement")
public class LoanDisbursement extends BaseEntity implements Serializable {
//    @Column(
//            name = "disbursement_date",
//            nullable = false
//    )
//    @UpdateTimestamp
//    LocalDateTime disbursementDate;

    //    @Column(
//            name = "disbursement_amount",
//            nullable = false
//    )
//    private double disbursementAmount;

    @Min(1)
    @Max(31)
    @Column(
            name = "monthly_payment_day",
            nullable = false
    )
    @Builder.Default
    int monthlyPaymentDay = 10;

    @ManyToOne
    @JoinColumn(
            name = "loan_detail_id"
    )
    LoanDetail loanDetail;
}
