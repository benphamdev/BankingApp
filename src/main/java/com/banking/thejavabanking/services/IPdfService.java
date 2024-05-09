package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.ExportTransactionToPdfRequest;

public interface IPdfService {
    void exportTransactionToPdf(
            ExportTransactionToPdfRequest exportTransactionToPdfRequest
    );
}
