package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.Enums.LoanPaymentStatus;
import com.banking.thejavabanking.models.Enums.LoanStatus;
import com.banking.thejavabanking.models.abstractions.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "tbl_loan_detail")
public class LoanDetail extends BaseAuditEntity<Integer> implements Serializable {
    @Column(name = "loan_status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    LoanStatus loanStatus = LoanStatus.PENDING;

    @Column(name = "loan_payment_status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    LoanPaymentStatus loanPaymentStatus = LoanPaymentStatus.UNPAID;

    @Column(name = "reference_number")
    String referenceNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_info_id")
    LoanInfo loanInfo;

    @OneToMany(
            mappedBy = "loanDetail",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<LoanDisbursement> loanDisbursements = new ArrayList<>();

    @OneToMany(
            mappedBy = "loanDetail",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<LoanPayment> loanPayments = new ArrayList<>();
}
