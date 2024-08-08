package com.banking.thejavabanking.domain.accounts.entity;

import com.banking.thejavabanking.contract.abstractions.shared.DateTrackingBase;
import com.banking.thejavabanking.domain.common.constants.Enums;
import com.banking.thejavabanking.domain.users.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends DateTrackingBase<Integer> implements Serializable {
    @Column(name = "account_number")
    String accountNumber;

    @Column(name = "account_type")
    Enums.AccountType accountType;

    @Column(name = "balance")
    BigDecimal balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    List<UserTransaction> userTransactions = new ArrayList<>();
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

    public void saveTransaction(UserTransaction userTransaction) {
        if (userTransaction != null) {
            if (userTransactions == null) {
                userTransactions = new ArrayList<>();
            }
            userTransactions.add(userTransaction);
            userTransaction.setAccount(this);
        }
    }
}
