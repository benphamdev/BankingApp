package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.*;
import com.banking.thejavabanking.dto.respones.AccountInfoResponse;
import com.banking.thejavabanking.dto.respones.UserResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.banking.thejavabanking.mapper.AccountMapper;
import com.banking.thejavabanking.models.Enums;
import com.banking.thejavabanking.models.entity.Account;
import com.banking.thejavabanking.models.entity.BranchInfo;
import com.banking.thejavabanking.models.entity.User;
import com.banking.thejavabanking.repositories.AccountRepository;
import com.banking.thejavabanking.repositories.BranchInfoRepository;
import com.banking.thejavabanking.repositories.UserRepository;
import com.banking.thejavabanking.services.IAccountService;
import com.banking.thejavabanking.services.ITransactionService;
import com.banking.thejavabanking.services.IUserService;
import com.banking.thejavabanking.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Slf4j
public class AccountServiceImpl implements IAccountService {
    AccountRepository accountRepository;
    UserRepository userRepository;
    BranchInfoRepository branchInfoRepository;
    ITransactionService iTransactionService;
    IUserService userService;
    AccountMapper accountMapper;
    //    IEmailService emailService;

    @Override
    public AccountInfoResponse createAccount(AccountCreationRequest accountRequest) {
        User user = userRepository.findUserById(accountRequest.getUserId())
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));

        BranchInfo branchInfo = branchInfoRepository.findById(accountRequest.getBranchInfoId())
                                                    .orElseThrow(() -> new AppException(
                                                            ErrorResponse.BRANCH_NOT_FOUND));

        if (user.getAccount() != null)
            throw new AppException(ErrorResponse.ACCOUNT_EXISTS);

        Account account = Account.builder()
                                 .accountNumber(AccountUtils.generateAccountNumber())
                                 .balance(BigDecimal.ZERO)
                                 .accountType(Enums.AccountType.CURRENT)// fix request with enums
                                 .build();

        user.setAccount(account);
        account.setUser(user);
        account.setBranchInfo(branchInfo);

        accountRepository.save(account);

        return AccountInfoResponse.builder()
                                  .accountNumber(account.getAccountNumber())
                                  .accountBalance(account.getBalance())
                                  .accountName(user.getFirstName())
                                  .build();
    }

    @Override
    public List<AccountInfoResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                                .map(accountMapper::toAccountInfoResponse)
                                .toList();
    }

    @Override
    public Optional<AccountInfoResponse> getAccountById(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);
        return Optional.ofNullable(accountMapper.toAccountInfoResponse(account));
    }

    @Override
    public void deleteAccountById(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account updateAccount(Integer id, Account accountRequest) {
        return null;
    }

    @Override
    public Optional<Account> getAccountByUserId(Integer userId) {
        User user = userRepository.findUserById(userId)
                                  .orElseThrow(() -> new AppException(ErrorResponse.USER_NOT_FOUND));
        return Optional.ofNullable(user.getAccount());
    }

    @Override
    public AccountInfoResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        Account account = getAccountByAccountNumber(enquiryRequest.getAccountNumber());

        UserResponse user = userService.getUserReponeseByID(account.getUser().getId());

        return
                AccountInfoResponse.builder()
                                   .accountNumber(account.getAccountNumber())
                                   .accountBalance(account.getBalance())
                                   .accountName(user.getFirstName())
                                   .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        Account account = getAccountByAccountNumber(enquiryRequest.getAccountNumber());

        log.info("Account name: {}", account.getUser().getId());
        User user = account.getUser();
        return user.getOtherName();
    }

    @Override
    public AccountInfoResponse creditAccount(CreditDebitRequest enquiryRequest) {
        Account account = getAccountByAccountNumber(enquiryRequest.getAccountNumber());

        UserResponse user = userService.getUserReponeseByID(account.getUser().getId());

        BigDecimal amount = enquiryRequest.getAmount();

        account.setBalance(account.getBalance()
                                  .add(amount));

        TransactionDto transactionDto = TransactionDto.builder()
                                                      .transactionType(Enums.TransactionType.DEPOSIT.toString())
                                                      .amount(amount)
                                                      .fromAccount(account.getAccountNumber())
                                                      .toAccount("SELF")
                                                      .status("SUCCESS")
                                                      .build();

        iTransactionService.saveTransaction(transactionDto);

        return
                AccountInfoResponse.builder()
                                   .accountNumber(account.getAccountNumber())
                                   .accountBalance(account.getBalance())
                                   .accountName(user.getLastName())
                                   .build();
    }

    @Override
    public AccountInfoResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        Account account = getAccountByAccountNumber(creditDebitRequest.getAccountNumber());

        UserResponse user = userService.getUserReponeseByID(account.getUser().getId());

        BigDecimal amount = creditDebitRequest.getAmount();

        if (account.getBalance()
                   .compareTo(amount) < 0) {
            throw new AppException(ErrorResponse.INSUFFICIENT_BALANCE);
        }

        account.setBalance(account.getBalance()
                                  .subtract(amount));

        accountRepository.save(account);
        TransactionDto transactionDto = TransactionDto.builder()
                                                      .transactionType(Enums.TransactionType.WITHDRAWAL.toString())
                                                      .amount(amount)
                                                      .fromAccount(account.getAccountNumber())
                                                      .toAccount("SELF")
                                                      .status("SUCCESS")
                                                      .build();

        iTransactionService.saveTransaction(transactionDto);

        return AccountInfoResponse.builder()
                                  .accountNumber(account.getAccountNumber())
                                  .accountBalance(account.getBalance())
//                                                                    .accountName(user.getFirst_name() + " " + user.getLast_name() + " " + user.getOther_name())
                                  .build();

    }

    @Override
    public AccountInfoResponse transfer(TransferRequest transferRequest) {
        Account fromAccount = getAccountByAccountNumber(transferRequest.getFromAccountNumber());
        Account toAccount = getAccountByAccountNumber(transferRequest.getToAccountNumber());

        UserResponse fromUser = userService.getUserReponeseByID(fromAccount.getUser().getId()),
                toUser = userService.getUserReponeseByID(toAccount.getUser().getId());

        BigDecimal amount = transferRequest.getAmount();

        if (fromAccount.getBalance()
                       .compareTo(amount) < 0) {
            throw new AppException(ErrorResponse.INSUFFICIENT_BALANCE);
        }

        String fromAccountName = fromUser.getFirstName(),
                toAccountName = toUser.getFirstName();

        // Debit from sender and credit to receiver
        fromAccount.setBalance(fromAccount.getBalance()
                                          .subtract(amount));
        toAccount.setBalance(toAccount.getBalance()
                                      .add(amount));

        // Send email notification not needed for now
//        EmailDetailRequest emailDebitAlert = EmailDetailRequest.builder()
//                                                               .subject(
//                                                                       "Account Transfer Notification")
//                                                               .recipient(fromUser.getEmail())
//                                                               .message("You have sent " + amount + " to " + toAccountName)
//                                                               .build();
//
//        EmailDetailRequest emailCreditAlert = EmailDetailRequest.builder()
//                                                                .subject(
//                                                                        "Account Transfer Notification")
//                                                                .recipient(toUser.getEmail())
//                                                                .message("You have received " + amount + " from " + fromAccountName)
//                                                                .build();
//
//        emailService.sendEmail(emailDebitAlert);
//        emailService.sendEmail(emailCreditAlert);

        // save transaction
        TransactionDto transactionDto = TransactionDto.builder()
                                                      .transactionType("TRANSFER")
                                                      .amount(amount)
                                                      .fromAccount(fromAccount.getAccountNumber())
                                                      .toAccount(toAccount.getAccountNumber())
                                                      .status("SUCCESS")
                                                      .build();

        iTransactionService.saveTransaction(transactionDto);

        return AccountInfoResponse.builder()
                                  .accountNumber(fromAccount.getAccountNumber())
                                  .accountBalance(fromAccount.getBalance())
                                  .accountName(fromAccountName)
                                  .build();
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.getAccountByAccountNumber(accountNumber)
                                .orElseThrow(() -> new RuntimeException(
                                        "Account not found"));
    }
}
