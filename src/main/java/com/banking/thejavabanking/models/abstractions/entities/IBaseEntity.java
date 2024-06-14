package com.banking.thejavabanking.models.abstractions.entities;

public interface IBaseEntity<T> {
    T getId();

    void setId(T id);
}
