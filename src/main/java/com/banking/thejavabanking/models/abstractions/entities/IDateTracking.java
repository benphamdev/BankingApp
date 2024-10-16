package com.banking.thejavabanking.models.abstractions.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IDateTracking {
    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDate getModifiedAt();

    void setModifiedAt(LocalDate modifiedAt);
}
