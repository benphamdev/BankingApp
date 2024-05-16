package com.banking.thejavabanking.models;

public class Enums {
    public enum AccountType {
        SAVINGS, CURRENT
    }

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    public enum Role {
        ADMIN, USER
    }

    public enum UserTypes {
        CUSTOMER, STAFF
    }

    public enum LoanTerm {
        THREE_MONTHS, SIX_MONTHS, NINE_MONTHS, TWELVE_MONTHS
    }

    public enum LoanStatus {
        PENDING, APPROVED, REJECTED
    }

    public enum LoanPaymentStatus {
        PAID, UNPAID
    }

    public enum AccountStatus {
        ACTIVE, INACTIVE
    }

    public enum InterestType {
        SIMPLE, COMPOUND
    }

    public enum OtpStatus {
        DELIVERED, FAILED
    }

    public enum Provinces {
        ANGIANG,
        BARIAVUNGTAU,
        BACLIEU,
        BACGIANG,
        BACKAN,
        BACNINH,
        BENTRE,
        BINHDUONG,
        BINHDINH,
        BINHPHUOC,
        BINHTHUAN,
        CAMAU,
        CAOBANG,
        CANTHO,
        DANANG,
        DAKLAK,
        DAKNONG,
        DIENBIEN,
        DONGNAI,
        DONGTHAP,
        GIALAI,
        HAGIANG,
        HANAM,
        HANOI,
        HATINH,
        HAIDUONG,
        HAIPHONG,
        HAUGIANG,
        HOABINH,
        HUNGYEN,
        KHANHHOA,
        KIENGIANG,
        KONTUM,
        LAICHAU,
        LANGSON,
        LAOCAI,
        LAMDONG,
        LONGAN,
        LONGXUYEN,
        NAMDINH,
        NGHEAN,
        NINHBINH,
        NINHTHUAN,
        PHUTHO,
        PHUYEN,
        QUANGBINH,
        QUANGNAM,
        QUANGNGAI,
        QUANGNINH,
        QUANGTRI,
        SONLA,
        TAYNINH,
        THAIBINH,
        THAINGUYEN,
        THANHHOA,
        THUATHIENHUE,
        TIENGIANG,
        TPHCM,
        TRAVINH,
        TUYENQUANG,
        VINHLONG,
        VINHPHUC,
        YENBAI;
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum TransactionStatus {
        SUCCESS, FAILED
    }
}
