package com.banking.thejavabanking.domain.accounts.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class AccountCreationRequest implements Serializable {
    private int userId;
    private int branchInfoId;
//    Enums.AccountType accountType;
}
