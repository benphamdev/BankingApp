package com.banking.thejavabanking.domain.accounts.services.impl;

import com.banking.thejavabanking.application.mapper.AccountMapper;
import com.banking.thejavabanking.domain.accounts.dto.requests.*;
import com.banking.thejavabanking.domain.accounts.dto.responses.AccountInfoResponse;
import com.banking.thejavabanking.domain.accounts.entity.Account;
import com.banking.thejavabanking.domain.accounts.entity.BranchInfo;
import com.banking.thejavabanking.domain.accounts.entity.UserTransaction;
import com.banking.thejavabanking.domain.accounts.repositories.AccountRepository;
import com.banking.thejavabanking.domain.accounts.repositories.BranchInfoRepository;
import com.banking.thejavabanking.domain.accounts.services.IAccountService;
import com.banking.thejavabanking.domain.accounts.services.ITransactionService;
import com.banking.thejavabanking.domain.common.constants.Enums;
import com.banking.thejavabanking.domain.common.constants.EnumsErrorResponse;
import com.banking.thejavabanking.domain.common.exceptions.AppException;
import com.banking.thejavabanking.domain.notifications.dto.requests.NotificationMessageDTO;
import com.banking.thejavabanking.domain.notifications.services.impl.FirebaseMessagingServiceImpl;
import com.banking.thejavabanking.domain.users.dto.respones.UserResponse;
import com.banking.thejavabanking.domain.users.entities.User;
import com.banking.thejavabanking.domain.users.repositories.UserRepository;
import com.banking.thejavabanking.domain.users.services.IUserService;
import com.banking.thejavabanking.infrastructure.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.banking.thejavabanking.infrastructure.utils.AccountUtils.convertTime;

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
    FirebaseMessagingServiceImpl firebaseMessagingService;

    @NonFinal final
    String image = "https://th.bing.com/th/id/OIP.GPgOs_sd9nF8fsKDOJe9dQHaEo?rs=1&pid=ImgDetMain";
    @NonFinal final
    String title = "Biến động số dư";

    @Override
    public Integer createAccount(AccountCreationRequest accountRequest) {
        User user = userRepository.findUserById(accountRequest.getUserId())
                                  .orElseThrow(() -> new AppException(EnumsErrorResponse.USER_NOT_FOUND));

        BranchInfo branchInfo = branchInfoRepository.findById(accountRequest.getBranchInfoId())
                                                    .orElseThrow(() -> new AppException(
                                                            EnumsErrorResponse.BRANCH_NOT_FOUND));

        if (user.getAccount() != null)
            throw new AppException(EnumsErrorResponse.ACCOUNT_EXISTS);

        Account account = Account.builder()
                                 .accountNumber(AccountUtils.generateAccountNumber())
                                 .balance(BigDecimal.ZERO)
                                 .accountType(Enums.AccountType.CURRENT)// fix request with enums
                                 .build();

        user.setAccount(account);
        account.setUser(user);
        account.setBranchInfo(branchInfo);

        accountRepository.save(account);

        return account.getId();
    }

    @Override
    public List<AccountInfoResponse> getAllAccounts() {
        return accountRepository.findAll()
                                .stream()
                                .map(accountMapper::toAccountInfoResponse)
                                .toList();
    }

    @Override
    public Optional<AccountInfoResponse> getAccountById(Integer id) {
        Account account = accountRepository.findById(id)
                                           .orElse(null);
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
    public AccountInfoResponse getAccountInforByUserId(Integer userId) {
        User user = userRepository.findUserById(userId)
                                  .orElseThrow(() -> new AppException(EnumsErrorResponse.USER_NOT_FOUND));

        Account account = user.getAccount();
        return accountMapper.toAccountInfoResponse(account);
    }

    @Override
    public Optional<Account> getAccountByUserId(Integer userId) {
        User user = userRepository.findUserById(userId)
                                  .orElseThrow(() -> new AppException(EnumsErrorResponse.USER_NOT_FOUND));

        Account account = user.getAccount();
        return Optional.ofNullable(account);
    }

    @Override
    public AccountInfoResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        Account account = getAccountByAccountNumber(enquiryRequest.getAccountNumber());

        UserResponse user = userService.getUserResponseByID(account.getUser()
                                                                   .getId());

        log.info(
                "Account name: {}",
                account.getUser()
                       .getId()
        );

        return accountMapper.toAccountInfoResponse(account);
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        Account account = getAccountByAccountNumber(enquiryRequest.getAccountNumber());

        log.info(
                "Account name: {}",
                account.getUser()
                       .getId()
        );
        User user = account.getUser();
        return user.getFirstName();
    }

    @Override
    public AccountInfoResponse creditAccount(CreditDebitRequest enquiryRequest) {
        Account account = getAccountByAccountNumber(enquiryRequest.getAccountNumber());

//        UserResponse user = userService.getUserResponseByID(account.getUser().getId());

        BigDecimal amount = enquiryRequest.getAmount();

        account.setBalance(account.getBalance()
                                  .add(amount));

        TransactionDto transactionDto = TransactionDto.builder()
                                                      .amount(amount)
                                                      .fromAccount(account.getAccountNumber())
                                                      .toAccount("SELF")
                                                      .description("Deposit money")
                                                      .build();

        UserTransaction userTransaction = convertToUserTransaction(
                transactionDto,
                Enums.TransactionType.DEPOSIT
        );
        iTransactionService.saveTransaction(userTransaction);
        account.saveTransaction(userTransaction);

        accountRepository.save(account);

        // send notification

        sendNotification(
                account.getUser()
                       .getPhoneToken()
                       .getToken(),
                createBodyNotification(userTransaction, account),
                Map.of("amount", amount.toString())
        );

        return accountMapper.toAccountInfoResponse(account);
    }

    @Override
    public AccountInfoResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        Account account = getAccountByAccountNumber(creditDebitRequest.getAccountNumber());

        BigDecimal amount = creditDebitRequest.getAmount();

        if (account.getBalance()
                   .compareTo(amount) < 0)
            throw new AppException(EnumsErrorResponse.INSUFFICIENT_BALANCE);

        account.setBalance(account.getBalance()
                                  .subtract(amount));

        accountRepository.save(account);
        TransactionDto transactionDto = TransactionDto.builder()
                                                      .amount(amount)
                                                      .fromAccount(account.getAccountNumber())
                                                      .toAccount("SELF")
                                                      .description("Withdraw money")
                                                      .build();

        UserTransaction userTransaction = convertToUserTransaction(
                transactionDto,
                Enums.TransactionType.WITHDRAWAL
        );
        iTransactionService.saveTransaction(userTransaction);
        account.saveTransaction(userTransaction);

        accountRepository.save(account);

        // send notification

        sendNotification(
                account.getUser()
                       .getPhoneToken()
                       .getToken(),
                createBodyNotification(userTransaction, account),
                Map.of("amount", amount.toString())
        );
        return accountMapper.toAccountInfoResponse(account);
    }

    @Override
    public AccountInfoResponse transfer(TransferRequest transferRequest) {
        Account fromAccount = getAccountByAccountNumber(transferRequest.getFromAccountNumber());
        Account toAccount = getAccountByAccountNumber(transferRequest.getToAccountNumber());

        UserResponse fromUser = userService.getUserResponseByID(fromAccount.getUser()
                                                                           .getId()),
                toUser = userService.getUserResponseByID(toAccount.getUser()
                                                                  .getId());

        BigDecimal amount = transferRequest.getAmount();

        if (fromAccount.getBalance()
                       .compareTo(amount) < 0) {
            throw new AppException(EnumsErrorResponse.INSUFFICIENT_BALANCE);
        }

        String fromAccountName = fromUser.getFirstName(),
                toAccountName = toUser.getFirstName();

        // Debit from sender and credit to receiver
        fromAccount.setBalance(fromAccount.getBalance()
                                          .subtract(amount));
        toAccount.setBalance(toAccount.getBalance()
                                      .add(amount));

        // save transaction
        TransactionDto transactionDto = TransactionDto.builder()
                                                      .amount(amount)
                                                      .fromAccount(fromAccount.getAccountNumber())
                                                      .toAccount(toAccount.getAccountNumber())
                                                      .description(transferRequest.getDescription())
                                                      .build();

        UserTransaction userTransaction = convertToUserTransaction(
                transactionDto,
                Enums.TransactionType.TRANSFER
        );
        iTransactionService.saveTransaction(userTransaction);
        fromAccount.saveTransaction(userTransaction);
        toAccount.saveTransaction(userTransaction);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        iTransactionService.saveTransaction(convertToUserTransaction(
                transactionDto,
                Enums.TransactionType.TRANSFER
        ));

        // send notification
        sendNotification(
                fromAccount.getUser()
                           .getPhoneToken()
                           .getToken(),
                createBodyNotification(userTransaction, fromAccount),
                Map.of("amount", amount.toString())
        );
        sendNotification(
                toAccount.getUser()
                         .getPhoneToken()
                         .getToken(),
                createBodyNotification(userTransaction, toAccount),
                Map.of("amount", amount.toString())
        );

        return accountMapper.toAccountInfoResponse(fromAccount);
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.getAccountByAccountNumber(accountNumber)
                                .orElseThrow(() -> new AppException(EnumsErrorResponse.ACCOUNT_NOT_FOUND));
    }

    public void updateAccount(Account account) {

    }

    public UserTransaction convertToUserTransaction(
            TransactionDto transactionDto, Enums.TransactionType transactionType
    ) {
        return UserTransaction.builder()
                              .transactionType(transactionType)
                              .amount(transactionDto.getAmount())
                              .fromAccount(transactionDto.getFromAccount())
                              .toAccount(transactionDto.getToAccount())
                              .account(getAccountByAccountNumber(transactionDto.getFromAccount()))
                              .status(Enums.TransactionStatus.SUCCESS)
                              .description(transactionDto.getDescription())
                              .build();
    }

    public void sendNotification(
            String token, String body, Map<String, String> data
    ) {
        NotificationMessageDTO notificationMessageDTO = NotificationMessageDTO.builder()
                                                                              .recipientToken(token)
                                                                              .title(title)
                                                                              .body(body)
                                                                              .image("")
                                                                              .data(data)
                                                                              .build();
        firebaseMessagingService.sendNotification(notificationMessageDTO);
        log.info("Notification sent successfully");
    }

    public String createBodyNotification(UserTransaction userTransaction, Account account) {
        String transactionAmount = userTransaction.getAmount()
                                                  .toString();
        Enums.TransactionType transactionType = userTransaction.getTransactionType();

        if (transactionType.equals(Enums.TransactionType.DEPOSIT))
            transactionAmount = "+" + transactionAmount + "VND";
        else if (transactionType.equals(Enums.TransactionType.WITHDRAWAL))
            transactionAmount = "-" + transactionAmount + "VND";

        return "Time: " + convertTime(userTransaction.getCreatedAt()) + "\n" +
                "Account: " + account.getAccountNumber() + "\n" +
                "Transaction amount: " + transactionAmount + "VND\n" +
                "Current balance: " + account.getBalance() + "VND";
    }
}
