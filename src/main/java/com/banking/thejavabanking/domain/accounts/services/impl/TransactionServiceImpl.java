package com.banking.thejavabanking.domain.accounts.services.impl;

import com.banking.thejavabanking.contract.abstractions.shared.response.PageResponse;
import com.banking.thejavabanking.domain.accounts.entity.UserTransaction;
import com.banking.thejavabanking.domain.accounts.repositories.AccountRepository;
import com.banking.thejavabanking.domain.accounts.repositories.TransactionRepository;
import com.banking.thejavabanking.domain.accounts.services.ITransactionService;
import com.banking.thejavabanking.domain.notifications.dto.requests.ExportTransactionToPdfRequest;
import com.banking.thejavabanking.domain.notifications.services.impl.PdfServiceImpl;
import com.banking.thejavabanking.domain.users.dto.respones.UserTransactionResponse;
import com.banking.thejavabanking.domain.users.repositories.search.UserTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.banking.thejavabanking.infrastructure.constants.AppConst.SORT_BY;
import static com.banking.thejavabanking.infrastructure.utils.AccountUtils.convertTime;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(
        level = lombok.AccessLevel.PRIVATE,
        makeFinal = true
)
public class TransactionServiceImpl implements ITransactionService {
    TransactionRepository transactionRepository;
    PdfServiceImpl pdfService;
    UserTransactionRepository userTransactionRepository;
    AccountRepository accountRepository;

    private void logTransactionDate(
            String accountNumber, LocalDate startDate, LocalDate endDate
    ) {
        log.info(
                "Getting transactions from database with account number: {} and date between {} and {}",
                accountNumber,
                startDate,
                endDate
        );
    }

    @Override
    public void saveTransaction(UserTransaction transaction) {
        // Save transaction to database
        transactionRepository.save(transaction);
    }

    @Override
    public List<UserTransaction> transactionList() {
        return transactionRepository.findAll();
    }

    @Override
    public List<UserTransaction> getTransactionWithAccountID(Integer accountID) {
        return transactionRepository.findUserTransactionByAccountId(accountID);
    }

    @Override
    public List<UserTransaction> getTransactions(
            String accountNumber, LocalDate startDate, LocalDate endDate
    ) {
        List<UserTransaction> temp = transactionRepository.findByFromAccountAndCreatedAtBetween(
                accountNumber, startDate, endDate
        );

        return temp;
    }

    @Override
    public void exportTransactionPdf(
            String accountNumber, LocalDate startDate, LocalDate endDate
    ) {
        List<UserTransaction> temp = transactionRepository.findByFromAccountAndCreatedAtBetween(
                accountNumber, startDate, endDate
        );
        logTransactionDate(accountNumber, startDate, endDate);

        // export transactions to pdf
        String pathfile = "src/main/resources/bank_statement/" + UUID.randomUUID() + ".pdf";

        exportTransactionsToPdf(
                ExportTransactionToPdfRequest.builder()
                                             .transactions(temp)
                                             .pdfPath(pathfile)
                                             .startDate(startDate)
                                             .endDate(endDate)
                                             .accountNumber(accountNumber)
                                             .build()
        );
    }

    public void exportTransactionsToPdf(
            ExportTransactionToPdfRequest exportTransactionToPdfRequest
    ) {
        log.info("Exporting transactions to PDF");
        pdfService.exportTransactionToPdf(exportTransactionToPdfRequest);
    }

    @Override
    public PageResponse<?> getAllTransactionWithBySort(int pageNo, int pageSize, String sortBy) {
        if (pageNo > 0) pageNo--;
        List<Sort.Order> orders = new ArrayList<>();
        if (StringUtils.hasLength(sortBy)) {
            // created_id:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find())
                orders.add(new Sort.Order(
                        Sort.Direction.fromString(matcher.group(3)),
                        matcher.group(1)
                ));
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));
        Page<UserTransaction> userTransactions = transactionRepository.findAll(pageable);
        return PageResponse.builder()
                           .page(userTransactions.getNumber())
                           .size(userTransactions.getSize())
                           .total(userTransactions.getTotalPages())
                           .items(userTransactions.stream()
                                                  .toList())
                           .build();
    }

    public PageResponse<?> getAllTransactionCurrentUserWithBySort(
            Integer accountId, int pageNo, int pageSize, String sortBy
    ) {
        if (pageNo > 0) pageNo--;
        List<Sort.Order> orders = new ArrayList<>();
        if (StringUtils.hasLength(sortBy)) {
            // created_id:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find())
                orders.add(new Sort.Order(
                        Sort.Direction.fromString(matcher.group(3)),
                        matcher.group(1)
                ));
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));
        Page<UserTransaction> userTransactions = transactionRepository.findUserTransactionByAccount(
                accountRepository.findById(accountId)
                                 .orElseThrow(),
                pageable
        );
        return PageResponse.builder()
                           .page(userTransactions.getNumber())
                           .size(userTransactions.getSize())
                           .total(userTransactions.getTotalPages())
                           .items(userTransactions.stream()
                                                  .map(this::convertUserTransactionResponse)
                                                  .toList())
                           .build();
    }

    public UserTransactionResponse convertUserTransactionResponse(UserTransaction userTransaction) {
        return UserTransactionResponse.builder()
                                      .time(convertTime(userTransaction.getCreatedAt()))
                                      .account(userTransaction.getAccount()
                                                              .getAccountNumber())
                                      .transactionAmount(userTransaction.getAmount()
                                                                        .toString() + " VND")
                                      .currentBalance(userTransaction.getAccount()
                                                                     .getBalance()
                                                                     .toString() + " VND")
                                      .content(userTransaction.getDescription())
                                      .build();
    }
}
