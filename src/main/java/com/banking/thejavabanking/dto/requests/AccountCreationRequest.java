package com.banking.thejavabanking.dto.requests;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class AccountCreationRequest implements Serializable {
    private int userId;
    private int branchInfoId;
//    Enums.AccountType accountType;
}
