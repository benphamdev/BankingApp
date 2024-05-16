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
@Table(name = "tbl_user_transaction")
public class UserTransaction extends BaseEntity implements Serializable {
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
