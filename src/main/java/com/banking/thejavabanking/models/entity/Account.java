package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.Enums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_account")
public class Account extends BaseEntity implements Serializable {
    @Column(name = "account_number")
    String accountNumber;

    @Column(name = "account_type")
    Enums.AccountType accountType;

    @Column(name = "balance")
    BigDecimal balance;

    @ManyToOne
    @JoinColumn(
            name = "branch_info_id"
    )
    @JsonIgnore
    private BranchInfo branchInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id"
    )
    @JsonIgnore
    private User user;
}
