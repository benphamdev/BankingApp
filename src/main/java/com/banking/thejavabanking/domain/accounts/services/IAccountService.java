package com.banking.thejavabanking.domain.accounts.services;

import com.banking.thejavabanking.domain.accounts.dto.requests.AccountCreationRequest;
import com.banking.thejavabanking.domain.accounts.dto.requests.CreditDebitRequest;
import com.banking.thejavabanking.domain.accounts.dto.requests.EnquiryRequest;
import com.banking.thejavabanking.domain.accounts.dto.requests.TransferRequest;
import com.banking.thejavabanking.domain.accounts.dto.responses.AccountInfoResponse;
import com.banking.thejavabanking.domain.accounts.entity.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Integer createAccount(AccountCreationRequest accountRequest);

    List<AccountInfoResponse> getAllAccounts();

    Optional<AccountInfoResponse> getAccountById(Integer id);

    void deleteAccountById(Integer id);

    Account updateAccount(Integer id, Account accountRequest);

    AccountInfoResponse getAccountInforByUserId(Integer userId);

    Optional<Account> getAccountByUserId(Integer userId);

    AccountInfoResponse balanceEnquiry(EnquiryRequest accountNumber);

    String nameEnquiry(EnquiryRequest enquiryRequest);

    AccountInfoResponse creditAccount(CreditDebitRequest accountNumber);

    AccountInfoResponse debitAccount(CreditDebitRequest accountNumber);

    AccountInfoResponse transfer(TransferRequest accountNumber);
}
