package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.models.entity.UserTransaction;
import com.banking.thejavabanking.services.impl.BankStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private BankStatement bankStatement;

    @GetMapping
    public List<UserTransaction> getBankStatement(
            @RequestParam String accountNumber,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return bankStatement.getTransactions(
                accountNumber,
                startDate,
                endDate
        );
    }
}
