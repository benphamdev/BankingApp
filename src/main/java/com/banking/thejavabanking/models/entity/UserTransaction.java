package com.banking.thejavabanking.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    String transactionType;

    @Column(name = "from_account")
    String fromAccount;

    @Column(name = "to_account")
    String toAccount;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    String status;
}
