package com.banking.thejavabanking.infrastructure.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {
    protected static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseISO(String date) {
        try {
            return ISO_DATE_FORMAT.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static long getDiffInDays(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }

    public static LocalDate parse(String date) {
        return LocalDate.parse(date);
    }
}
