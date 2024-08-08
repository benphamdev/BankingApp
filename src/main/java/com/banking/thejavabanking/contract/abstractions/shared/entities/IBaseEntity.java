package com.banking.thejavabanking.contract.abstractions.shared.entities;

public interface IBaseEntity<T> {
    T getId();

    void setId(T id);
}
