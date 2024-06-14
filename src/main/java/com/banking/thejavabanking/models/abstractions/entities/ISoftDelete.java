package com.banking.thejavabanking.models.abstractions.entities;

public interface ISoftDelete {
    boolean isDeleted();

    void setDeleted(boolean deleted);
}
