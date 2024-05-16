package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.requests.SavingRequest;
import com.banking.thejavabanking.models.entity.Saving;
import org.springframework.data.jpa.repository.Query;

public interface ISavingService {
    Saving createSavingAccount(SavingRequest savingRequest);

    void processMaturedSavings();

    void cancelSaving(Integer savingId);

    @Query(
            value = "SELECT * FROM tbl_saving WHERE user_id = ?1",
            nativeQuery = true
    )
    Saving getSavingByUserId(Integer userId);
}
