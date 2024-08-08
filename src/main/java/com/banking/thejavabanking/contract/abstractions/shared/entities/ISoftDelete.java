package com.banking.thejavabanking.contract.abstractions.shared.entities;

public interface ISoftDelete {
    boolean isDeleted();

    void setDeleted(boolean deleted);
}
