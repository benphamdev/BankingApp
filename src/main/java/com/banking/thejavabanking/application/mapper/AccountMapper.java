package com.banking.thejavabanking.application.mapper;

import com.banking.thejavabanking.domain.accounts.dto.responses.AccountInfoResponse;
import com.banking.thejavabanking.domain.accounts.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    // write response to request
    @Mapping(
            target = "accountBalance",
            source = "balance",
            qualifiedByName = "nullSafeDouble"
    )
    @Mapping(
            target = "accountNumber",
            source = "accountNumber"
    )
    @Mapping(
            target = "branchName",
            source = "branchInfo.branchName",
            qualifiedByName = "nullSafeString"
    )
    @Mapping(
            target = "accountName",
            source = "user.firstName",
            qualifiedByName = "nullSafeString"
    )
    AccountInfoResponse toAccountInfoResponse(Account account);

    @Named("nullSafeString")
    default String nullSafeString(String string) {
        return string != null ? string : "N/A";
    }

    @Named("nullSafeDouble")
    default BigDecimal nullSafeDouble(BigDecimal number) {
        return number != null ? number : BigDecimal.ZERO;
    }

    // Account toAccount(AccountCreationRequest accountCreationRequest);
    // Account toAccount(TransferRequest transferRequest);
    // Account toAccount(CreditDebitRequest creditDebitRequest);
    // Account toAccount(EnquiryRequest enquiryRequest);
}


