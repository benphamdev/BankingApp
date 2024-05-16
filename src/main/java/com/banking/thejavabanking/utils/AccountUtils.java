package com.banking.thejavabanking.utils;

import java.time.LocalDateTime;
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

    // convert time format : 2024-09-01T00:00:00.000+00:00 -> 01/09/2024 00:00
    public static String convertTime(LocalDateTime time) {
        return time.getDayOfMonth() + "/" + time.getMonthValue() + "/" + time.getYear() + " " + time.getHour() + ":" + time.getMinute();
    }
}
