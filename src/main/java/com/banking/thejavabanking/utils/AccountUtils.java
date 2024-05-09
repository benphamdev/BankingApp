package com.banking.thejavabanking.utils;

import java.time.Year;

public class AccountUtils {
    // 2024 + random 6 digits
    public static String generateAccountNumber() {
        Year year = Year.now();
        return year.toString() + generateNumber();
    }

    public static Integer generateNumber() {
        int min = 100000, max = 999999;
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
