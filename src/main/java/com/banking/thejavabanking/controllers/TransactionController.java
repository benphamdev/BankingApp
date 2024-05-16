package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.services.impl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(
            summary = "Get list of transactions between start date and end date",
            description = "Send a request via this API to get transaction list by account number, start date and end date"
    )
    @GetMapping
    public BaseResponse<?> getTransactionBetweenStartDateAndEndDate(
            @RequestParam String accountNumber,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return BaseResponse.builder()
                           .message("Get transactions successfully")
                           .data(transactionService.getTransactions(accountNumber, startDate, endDate))
                           .build();
    }

    @Operation(
            summary = "Export list of transactions between start date and end date",
            description = "Send a request via this API to export transaction list by account number, start date and end date"
    )
    @GetMapping("/export")
    public BaseResponse<?> exportTransactionBetweenStartDateAndEndDate(
            @RequestParam String accountNumber,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        transactionService.exportTransactionPdf(accountNumber, startDate, endDate);
        return BaseResponse.builder()
                           .message("Get transactions successfully")
                           .build();
    }

    @Operation(
            summary = "Get all transactions",
            description = "Send a request via this API to get all transactions"
    )
    @GetMapping("/all")
    public BaseResponse<?> getAllTransactions() {
        return BaseResponse.builder()
                           .message("Get all transactions successfully")
                           .data(transactionService.transactionList())
                           .build();
    }

    @Operation(
            summary = "Get list of transactions with account ID",
            description = "Send a request via this API to get transaction list by account ID"
    )
    @GetMapping("/{accountID}")
    public BaseResponse<?> getTransactionWithAccountID(@PathVariable Integer accountID) {
        return BaseResponse.builder()
                           .message("Get transactions with account ID successfully")
                           .data(transactionService.getTransactionWithAccountID(accountID))
                           .build();
    }

    @Operation(
            summary = "Get list of transactions with paging and sorting",
            description = "Send a request via this API to get transaction list by pageNo, pageSize and sort by 1 column"
    )
    @GetMapping("/list-with-sort")
    public BaseResponse<?> getTransactionsWithSort(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        return BaseResponse.builder()
                           .message("get all transactions with by sort")
                           .data(transactionService.getAllTransactionWithBySort(pageNo, pageSize, sortBy))
                           .build();
    }

    @Operation(
            summary = "Get list of users and search with paging and sorting by customize query",
            description = "Send a request via this API to get user list by pageNo, pageSize and sort by multiple column"
    )
    @GetMapping("/list-user-and-search-with-paging-and-sorting/{accountID}")
    public BaseResponse<?> getAllUsersWithPagingAndSorting(
            @PathVariable Integer accountID,
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "20", required = false) int pageSize,
//            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy
    ) {
        log.info("Request to get current transaction list by pageNo, pageSize and sort by 1 columns");
        return BaseResponse.builder()
                           .status(HttpStatus.OK.value())
                           .message("User list retrieved successfully")
                           .data(transactionService.getAllTransactionCurrentUserWithBySort(
                                   accountID,
                                   pageNo,
                                   pageSize,
                                   sortBy
                           ))
                           .build();
    }
}
