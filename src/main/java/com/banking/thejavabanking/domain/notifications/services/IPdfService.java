package com.banking.thejavabanking.domain.notifications.services;

import com.banking.thejavabanking.domain.notifications.dto.requests.ExportTransactionToPdfRequest;

public interface IPdfService {
    void exportTransactionToPdf(
            ExportTransactionToPdfRequest exportTransactionToPdfRequest
    );
}
