package com.banking.thejavabanking.dto.respones;

import com.banking.thejavabanking.models.entity.Account;
import com.banking.thejavabanking.models.entity.Province;
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
