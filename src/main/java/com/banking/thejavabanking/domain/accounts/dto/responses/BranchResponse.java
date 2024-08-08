package com.banking.thejavabanking.domain.accounts.dto.responses;

import com.banking.thejavabanking.domain.accounts.entity.Account;
import com.banking.thejavabanking.domain.accounts.entity.Province;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BranchResponse {
    private Integer id;

    private String branchName;

    private String address;

    private Province province;

    private List<Account> accounts;
}
