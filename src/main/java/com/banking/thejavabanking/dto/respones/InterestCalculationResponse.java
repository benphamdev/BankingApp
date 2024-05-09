package com.banking.thejavabanking.dto.respones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InterestCalculationResponse {
    private double interestAmountMonthly;
    private double interestAmount;
    private double totalAmount;

    public InterestCalculationResponse(double interestAmountMonthly, double interestAmount) {
        this.interestAmountMonthly = interestAmountMonthly;
        this.interestAmount = interestAmount;
        this.totalAmount = interestAmountMonthly + interestAmount;
    }
}
