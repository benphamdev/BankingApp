package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.SavingRequest;

public interface ISavingService {
    void createSavingAccount(SavingRequest savingRequest);

    void processMaturedSavings();

    void cancelSaving(int savingId);
}
