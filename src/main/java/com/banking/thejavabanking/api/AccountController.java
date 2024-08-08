package com.banking.thejavabanking.api;

import com.banking.thejavabanking.contract.abstractions.shared.response.BaseResponse;
import com.banking.thejavabanking.domain.accounts.dto.requests.AccountCreationRequest;
import com.banking.thejavabanking.domain.accounts.dto.requests.CreditDebitRequest;
import com.banking.thejavabanking.domain.accounts.dto.requests.EnquiryRequest;
import com.banking.thejavabanking.domain.accounts.dto.requests.TransferRequest;
import com.banking.thejavabanking.domain.accounts.dto.responses.AccountInfoResponse;
import com.banking.thejavabanking.domain.accounts.entity.Account;
import com.banking.thejavabanking.domain.accounts.services.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Slf4j
@Tag(name = "Account controller", description = "The Account API")
@Validated
public class AccountController {
    private final AccountServiceImpl accountService;

    @Autowired
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "Create Account",
            description = "Create an account"
    )
    @GetMapping("/create/{userId}/{branchInfoId}")
    public BaseResponse<Integer> createAccount(
            @PathVariable @Min(1) int userId, @PathVariable @Min(1) int branchInfoId
    ) {
        Integer newAccount = accountService.createAccount(
                new AccountCreationRequest(userId, branchInfoId));
        return BaseResponse.<Integer>builder()
                           .message("Account created successfully")
                           .data(newAccount)
                           .build();
    }

    @GetMapping("/all")
    public BaseResponse<List<AccountInfoResponse>> getAllAccounts() {
        List<AccountInfoResponse> accounts = accountService.getAllAccounts();
        return BaseResponse.<List<AccountInfoResponse>>builder()
                           .message("List of all accounts")
                           .data(accounts)
                           .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<AccountInfoResponse> getAccountById(@PathVariable @Min(1) Integer id) {
        AccountInfoResponse account = accountService.getAccountById(id)
                                                    .orElse(null);

        if (account == null)
            throw new RuntimeException("Account not found");

        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Account found")
                           .data(account)
                           .build();
    }

    // chua test
    @PutMapping("/{id}")
    public BaseResponse<Account> updateAccount(@PathVariable @Min(1) Integer id, Account account) {
        Account updatedAccount = accountService.updateAccount(id, account);
        return BaseResponse.<Account>builder()
                           .message("Account updated successfully")
                           .data(updatedAccount)
                           .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteAccount(@PathVariable @Min(1) Integer id) {
        accountService.deleteAccountById(id);
        return BaseResponse.<Void>builder()
                           .message("Account deleted successfully")
                           .build();
    }

    @GetMapping("/user/{userId}")
    public BaseResponse<AccountInfoResponse> getAccountByUserId(
            @PathVariable @Min(1) Integer userId
    ) {
        AccountInfoResponse account = accountService.getAccountInforByUserId(userId);

        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Account found")
                           .data(account)
                           .build();
    }

    @GetMapping("/balanceEnquiry")
    public BaseResponse<AccountInfoResponse> balanceEnquiry(
            @Valid @RequestBody EnquiryRequest request
    ) {
        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Balance enquiry successfully")
                           .data(accountService.balanceEnquiry(request))
                           .build();
    }

    @GetMapping("/nameEnquiry")
    public BaseResponse<String> nameEnquiry(@Valid @RequestBody EnquiryRequest request) {
        return BaseResponse.<String>builder()
                           .message("Name enquiry successfully")
                           .data(accountService.nameEnquiry(request))
                           .build();
    }

    @Operation(
            summary = "Credit Account",
            description = "Credit an account"
    )
    @PostMapping("/credit")
    public BaseResponse<AccountInfoResponse> credit(
            @Valid @RequestBody CreditDebitRequest request
    ) {
        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Credit account successfully")
                           .data(accountService.creditAccount(request))
                           .build();
    }

    @Operation(
            summary = "Debit Account",
            description = "Debit an account"
    )
    @PostMapping("/debit")
    public BaseResponse<AccountInfoResponse> debit(@Valid @RequestBody CreditDebitRequest request) {
        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Debit account successfully")
                           .data(accountService.debitAccount(request))
                           .build();
    }

    @PostMapping("/transfer")
    public BaseResponse<AccountInfoResponse> transfer(@Valid @RequestBody TransferRequest request) {
        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Transfer account successfully")
                           .data(accountService.transfer(request))
                           .build();
    }
}
