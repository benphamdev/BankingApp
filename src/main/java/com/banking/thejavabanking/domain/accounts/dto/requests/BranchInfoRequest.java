package com.banking.thejavabanking.domain.accounts.dto.requests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class BranchInfoRequest implements Serializable {
    private String branchName;
    private String address;
    private String provinceName;
}
