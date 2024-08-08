package com.banking.thejavabanking.domain.notifications.dto.requests;

import com.banking.thejavabanking.domain.accounts.entity.UserTransaction;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExportTransactionToPdfRequest implements Serializable {
    List<UserTransaction> transactions;

    String pdfPath;

    LocalDate startDate;

    LocalDate endDate;

    String accountNumber;
}
