package com.banking.thejavabanking.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "tbl_loan_info")
public class LoanInfo extends BaseEntity implements Serializable {
    @NotNull
    @Positive
    @Column(name = "loan_amount")
    double loanAmount;

    @NotNull
    @Positive
    @Column(name = "loan_term")
    int loanTerm; // input in months

    @Column(name = "interest_rate")
    @Min(0)
    @Max(100)
    @Builder.Default
    double interestRate = 12; // Default value: 12% / year
    //    @Column(name = "insterest_rate_margin")
//    @Min(0)
//    @Max(100)
//    private double interestRateMargin = 0.02;
//    @OneToOne(mappedBy = "loanInfo")
//    private LoanDetail loanDetail;
}
