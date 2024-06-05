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
        YENBAI
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum TransactionStatus {
        SUCCESS, FAILED
    }

    public enum SearchOperation {
        EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS;
        public static final String[] SIMPLE_OPERATION_SET = {":", "!", ">", "<", "~"};
        public static final String OR_PREDICATE_FLAG = "'";
        public static final String ZERO_OR_MORE_REGEX = "*";
        public static final String OR_OPERATOR = "OR";
        public static final String AND_OPERATOR = "AND";
        public static final String LEFT_PARANTHESIS = "(";
        public static final String RIGHT_PARANTHESIS = ")";

        public static SearchOperation getSimpleOperation(final char input) {
            return switch (input) {
                case ':' -> EQUALITY;
                case '!' -> NEGATION;
                case '>' -> GREATER_THAN;
                case '<' -> LESS_THAN;
                case '~' -> LIKE;
                default -> null;
            };
        }
    }
}
