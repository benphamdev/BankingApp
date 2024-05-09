package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.ExportTransactionToPdfRequest;
import com.banking.thejavabanking.models.entity.UserTransaction;
import com.banking.thejavabanking.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(
        level = lombok.AccessLevel.PRIVATE,
        makeFinal = true
)
public class BankStatement {
    TransactionRepository transactionRepository;
    PdfServiceImpl pdfService;

    public List<UserTransaction> getTransactions(
            String accountNumber, LocalDate startDate, LocalDate endDate
    ) {
        List<UserTransaction> temp = transactionRepository.findByFromAccountAndCreatedAtBetween(
                accountNumber, startDate, endDate
        );
        log.info("Getting transactions from database");

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

        return temp;
    }

    public void exportTransactionsToPdf(
            ExportTransactionToPdfRequest exportTransactionToPdfRequest
    ) {
        log.info("Exporting transactions to PDF");
        pdfService.exportTransactionToPdf(exportTransactionToPdfRequest);
    }
}
