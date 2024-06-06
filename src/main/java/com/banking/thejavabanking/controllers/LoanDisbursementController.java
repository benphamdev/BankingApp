package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.CreditDebitRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.models.entity.Account;
import com.banking.thejavabanking.models.entity.LoanDisbursement;
import com.banking.thejavabanking.services.IAccountService;
import com.banking.thejavabanking.services.ILoanDisbursementService;
import com.banking.thejavabanking.services.impl.AccountServiceImpl;
import com.banking.thejavabanking.services.impl.LoanDetailServiceImpl;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loan-disbursement")
@FieldDefaults(
        level = lombok.AccessLevel.PRIVATE,
        makeFinal = true
)
@RequiredArgsConstructor
public class LoanDisbursementController {
    ILoanDisbursementService loanDisbursementService;
    LoanDetailServiceImpl loanDetailService;
    IAccountService iAccountService;
    AccountServiceImpl accountService;

    @PostMapping
    public BaseResponse<?> createLoanDisbursement(
            @RequestBody LoanDisbursement loanDisbursement,
            @RequestParam Integer loanDetailId
    ) {
        var loanDetail = loanDetailService.getLoanDetailById(loanDetailId);

        if (loanDetail == null) {
            return new BaseResponse<>("Loan detail not found");
        }

        var userId = loanDetail.getUser()
                               .getId();

        Optional<Account> account = accountService.getAccountByUserId(userId);

        if (account.isEmpty()) {
            return new BaseResponse<>("Account not found");
        }
        iAccountService.creditAccount(
                CreditDebitRequest.builder()
                                  .accountNumber(account.get()
                                                        .getAccountNumber())
                                  .amount(BigDecimal.valueOf(loanDetail.getLoanInfo()
                                                                       .getLoanAmount()))
                                  .build()
        );

        loanDisbursement.setLoanDetail(loanDetail);

        return new BaseResponse<>(loanDisbursementService.createLoanDisbursement(loanDisbursement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDisbursement> getLoanDisbursementById(
            @PathVariable @Min(1) Integer id
    ) {
        return loanDisbursementService.getLoanDisbursementById(id)
                                      .map(ResponseEntity::ok)
                                      .orElse(ResponseEntity.notFound()
                                                            .build());
    }

    @GetMapping
    public BaseResponse<List<LoanDisbursement>> getLoanDisbursements() {

        return new BaseResponse<>(loanDisbursementService.getLoanDisbursements());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDisbursement> updateLoanDisbursement(
            @PathVariable @Min(1) Integer id,
            @RequestBody LoanDisbursement loanDisbursement
    ) {
        LoanDisbursement updatedLoanDisbursement =
                loanDisbursementService.createLoanDisbursement(loanDisbursement);
        return ResponseEntity.ok(updatedLoanDisbursement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanDisbursementById(
            @PathVariable @Min(1) Integer id
    ) {
        loanDisbursementService.deleteLoanDisbursementById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
