package com.banking.thejavabanking.dto.respones;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Builder
@Schema(
        description = "Account Information"
)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AccountInfoResponse {
    Integer id;

    @Schema(
            description = "Account Name",
            example = "John Doe"
    )
    String accountName;

    @Schema(
            description = "Account Balance",
            example = "1000.00"
    )

    BigDecimal accountBalance;
    @Schema(
            description = "Account Number",
            example = "1234567890"
    )
    String accountNumber;

    String branchName;
}
