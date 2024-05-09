package com.banking.thejavabanking.utils;

public class InterestUtils {
    // simple interest formula: I = P * R * T
//    P = Principal Amount
//    I = Interest Amount
//    r = Rate of Interest per year in decimal; r = R/100
//    R = Rate of Interest per year as a percent; R = r * 100
//    t = Time Periods involved
    public static double calculateSimpleInterest(double principal, double rate, int time) {
        return principal * (rate / 100) * (time / 12); // time in months
    }

    // compound interest formula: A = P(1 + r/n)^(nt)
//    A = final amount including interest,
//    P = principal amount,
//    r = annual interest rate (as decimal),
//    n = number of compounds per year, t = number of years.

    public static double calculateCompoundInterest(
            double principal, double rate, int time, int n
    ) {
        return principal * (Math.pow(1 + (rate / 100) / n, n * (time / 12)) - 1);
    }

    // Calculate interest for a given Interest or Compound Interest
    public static double calculateInterest(
            double principal, double rate, int time, int n, boolean isCompoundInterest
    ) {
        return isCompoundInterest
                ? calculateCompoundInterest(principal, rate, time, n)
                : calculateSimpleInterest(principal, rate, time);
    }

    // Principal for every month simple interest
    public static double calculateMonthlyPrincipalPayment(double principal, double time) {
        return principal / time;
    }

    // Interest for every month simple interest
    public static double calculateMonthlyInterestPayment(
            double principal, double rate, double time
    ) {
        double ratePerMonth = rate / 100;
        return principal * ratePerMonth / time;
    }

}
