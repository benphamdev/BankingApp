package com.banking.thejavabanking.domain.accounts.entity;

import com.banking.thejavabanking.contract.abstractions.shared.DateTrackingBase;
import com.banking.thejavabanking.domain.common.constants.Enums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user_transaction")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTransaction extends DateTrackingBase<Integer> implements Serializable {
    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    Enums.TransactionType transactionType;

    @Column(name = "from_account")
    String fromAccount;

    @Column(name = "to_account")
    String toAccount;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Enums.TransactionStatus status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;
}
