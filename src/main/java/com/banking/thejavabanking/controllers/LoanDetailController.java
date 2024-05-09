package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.LoanDetailRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.dto.respones.InterestCalculationResponse;
import com.banking.thejavabanking.models.Enums;
import com.banking.thejavabanking.models.entity.LoanDetail;
import com.banking.thejavabanking.services.impl.LoanDetailServiceImpl;
import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-detail")
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class LoanDetailController {
    LoanDetailServiceImpl loanDetailService;

    @GetMapping
    public BaseResponse<List<LoanDetail>> getAllLoanDetails() {
        return BaseResponse.<List<LoanDetail>>builder()
                           .message("Loan details fetched successfully")
                           .data(loanDetailService.getLoanDetails())
                           .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<LoanDetail> getLoanDetailById(
            @PathVariable Integer id
    ) {
        return BaseResponse.<LoanDetail>builder()
                           .message("Loan detail fetched successfully")
                           .data(loanDetailService.getLoanDetailById(id))
                           .build();
    }

    @PostMapping
    public BaseResponse<LoanDetail> saveLoanDetail(
            @RequestBody LoanDetailRequest loanDetail
    ) {
        return BaseResponse.<LoanDetail>builder()
                           .message("Loan detail saved successfully")
                           .data(loanDetailService.saveLoanDetail(loanDetail))
                           .build();
    }

    // error here
//    @PutMapping("/{id}")
//    @Transactional
//    public BaseResponse<LoanDetail> updateLoanDetail(
//            @PathVariable int id,
//            @RequestBody LoanDetail loanDetail
//    ) {
//        loanDetail.setId(id);
//        return BaseResponse.<LoanDetail>builder()
//                           .message("Loan detail updated successfully")
//                           .data(loanDetailService.saveLoanDetail(loanDetail))
//                           .build();
//    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteLoanDetailById(
            @PathVariable int id
    ) {
        loanDetailService.deleteLoanDetailById(id);
        return BaseResponse.<Void>builder()
                           .message("Loan detail deleted successfully")
                           .build();
    }

    // calculate interest for a given loan detail
    @GetMapping("interest/{id}")
    public BaseResponse<String> interestCalculator(
            @PathVariable int id,
            @PathParam("type") Enums.InterestType type
    ) {
        double interest = loanDetailService.calculateInterest(id, type);
        return BaseResponse.<String>builder()
                           .message("Interest calculated successfully")
                           .data(String.format("%.2f", interest))
                           .build();
    }

    @PutMapping("/{id}/approve")
    public BaseResponse<Void> approveLoanDetail(
            @PathVariable int id
    ) {
        LoanDetail loanDetail = loanDetailService.getLoanDetailById(id);
        loanDetailService.updateLoanDetail(loanDetail, Enums.LoanStatus.APPROVED);
        return BaseResponse.<Void>builder()
                           .message("Loan detail approved successfully")
                           .build();
    }

    @PutMapping("/{id}/deny")
    public BaseResponse<Void> denyLoanDetail(
            @PathVariable int id
    ) {
        LoanDetail loanDetail = loanDetailService.getLoanDetailById(id);
        loanDetailService.updateLoanDetail(loanDetail, Enums.LoanStatus.REJECTED);
        return BaseResponse.<Void>builder()
                           .message("Loan detail denied successfully")
                           .build();
    }

    @PutMapping("/{id}/status")
    public BaseResponse<Void> updateLoanDetailStatus(
            @PathVariable int id,
            @PathParam("status") Enums.LoanStatus status,
            @PathParam("paymentStatus") Enums.LoanPaymentStatus paymentStatus
    ) {
        LoanDetail loanDetail = loanDetailService.getLoanDetailById(id);
        loanDetailService.updateLoanDetail(loanDetail, status, paymentStatus);
        return BaseResponse.<Void>builder()
                           .message("Loan detail status updated successfully")
                           .build();
    }

    // calculate total amount to pay monthly = base amount + interest amount (simple interest)
    @GetMapping("/interest/final/{id}")
    public BaseResponse<InterestCalculationResponse> finalMonthlyAmountIncludingInterest(
            @PathVariable int id
    ) {
        return BaseResponse.<InterestCalculationResponse>builder()
                           .message("Interest calculated successfully")
                           .data(loanDetailService.finalMonthlyAmountIncludingInterest(id))
                           .build();
    }

    @GetMapping("/interest/decrease/{id}")
    public BaseResponse<List<InterestCalculationResponse>> decreaseInterestCalculation(
            @PathVariable int id
    ) {
        return BaseResponse.<List<InterestCalculationResponse>>builder()
                           .message("Interest calculated successfully")
                           .data(loanDetailService.calculateDecreasedInterestMonthly(id))
                           .build();
    }
}
