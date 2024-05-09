package com.banking.thejavabanking.dto.requests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class BranchUpdateRequest implements Serializable {
    private String branchName;
    private String address;
}
