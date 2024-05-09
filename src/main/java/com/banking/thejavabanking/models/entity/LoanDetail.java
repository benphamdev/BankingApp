package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.Enums.LoanPaymentStatus;
import com.banking.thejavabanking.models.Enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_loan_detail")
public class LoanDetail extends BaseEntity implements Serializable {
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

    @OneToOne(cascade = CascadeType.ALL)
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
