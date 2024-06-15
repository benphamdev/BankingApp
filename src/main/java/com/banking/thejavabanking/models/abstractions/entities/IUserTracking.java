package com.banking.thejavabanking.models.abstractions.entities;

public interface IUserTracking {
    String getCreatedBy();

    void setCreatedBy(String createdBy);

    String modifiedBy();

    void setModifiedBy(String modifiedBy);
}
