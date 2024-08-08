package com.banking.thejavabanking.contract.abstractions.shared.entities;

public interface IUserTracking {
    String getCreatedBy();

    void setCreatedBy(String createdBy);

    String modifiedBy();

    void setModifiedBy(String modifiedBy);
}
