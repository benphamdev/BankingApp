package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.AccountCreationRequest;
import com.banking.thejavabanking.dto.requests.CreditDebitRequest;
import com.banking.thejavabanking.dto.requests.EnquiryRequest;
import com.banking.thejavabanking.dto.requests.TransferRequest;
import com.banking.thejavabanking.dto.respones.AccountInfoResponse;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.models.entity.Account;
import com.banking.thejavabanking.services.impl.AccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.banking.thejavabanking.exceptions.ErrorResponse.ACCOUNT_NOT_FOUND;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountServiceImpl accountService;

    @Autowired
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public BaseResponse<AccountInfoResponse> createAccount(
            @RequestBody AccountCreationRequest account
    ) {
        AccountInfoResponse newAccount = accountService.createAccount(account);
        return BaseResponse.<AccountInfoResponse>builder()
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
    public BaseResponse<AccountInfoResponse> getAccountById(
            @PathVariable Integer id
    ) {
        AccountInfoResponse account = accountService.getAccountById(id).orElse(null);

        if (account == null)
            throw new RuntimeException("Account not found");

        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Account found")
                           .data(account)
                           .build();
    }

    // chua test
    @PutMapping("/{id}")
    public BaseResponse<Account> updateAccount(
            @PathVariable Integer id, Account account
    ) {
        Account updatedAccount = accountService.updateAccount(id, account);
        return BaseResponse.<Account>builder()
                           .message("Account updated successfully")
                           .data(updatedAccount)
                           .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteAccount(
            @PathVariable Integer id
    ) {
        accountService.deleteAccountById(id);
        return BaseResponse.<Void>builder()
                           .message("Account deleted successfully")
                           .build();
    }

    @GetMapping("/user/{userId}")
    public BaseResponse<Account> getAccountByUserId(
            @PathVariable Integer userId
    ) {
        Optional<Account> account = accountService.getAccountByUserId(userId);

        if (account.isEmpty())
            throw new AppException(ACCOUNT_NOT_FOUND);

        return BaseResponse.<Account>builder()
                           .message("Account found")
                           .data(account.get())
                           .build();
    }

    @GetMapping("/balanceEnquiry")
    public BaseResponse<AccountInfoResponse> balanceEnquiry(
            @Valid
            @RequestBody
            EnquiryRequest request
    ) {
        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Balance enquiry successfully")
                           .data(accountService.balanceEnquiry(request))
                           .build();
    }

    @GetMapping("/nameEnquiry")
    public BaseResponse<String> nameEnquiry(
            @Valid
            @RequestBody
            EnquiryRequest request
    ) {
        return BaseResponse.<String>builder()
                           .message("Name enquiry successfully")
                           .data(accountService.nameEnquiry(request))
                           .build();
    }

    @PostMapping("/credit")
    public BaseResponse<AccountInfoResponse> credit(
            @Valid
            @RequestBody
            CreditDebitRequest request
    ) {
        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Credit account successfully")
                           .data(accountService.creditAccount(request))
                           .build();
    }

    @PostMapping("/debit")
    public BaseResponse<AccountInfoResponse> debit(
            @Valid
            @RequestBody
            CreditDebitRequest request
    ) {
        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Debit account successfully")
                           .data(accountService.debitAccount(request))
                           .build();
    }

    @PostMapping("/transfer")
    public BaseResponse<AccountInfoResponse> transfer(
            @Valid
            @RequestBody
            TransferRequest request
    ) {
        return BaseResponse.<AccountInfoResponse>builder()
                           .message("Transfer account successfully")
                           .data(accountService.transfer(request))
                           .build();
    }
}
