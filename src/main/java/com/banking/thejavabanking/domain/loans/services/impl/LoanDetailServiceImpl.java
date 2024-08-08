package com.banking.thejavabanking.domain.loans.services.impl;

import com.banking.thejavabanking.domain.accounts.dto.requests.CreditDebitRequest;
import com.banking.thejavabanking.domain.accounts.dto.responses.InterestCalculationResponse;
import com.banking.thejavabanking.domain.accounts.repositories.AccountRepository;
import com.banking.thejavabanking.domain.accounts.services.impl.AccountServiceImpl;
import com.banking.thejavabanking.domain.common.constants.Enums;
import com.banking.thejavabanking.domain.common.constants.Enums.LoanPaymentStatus;
import com.banking.thejavabanking.domain.common.constants.Enums.LoanStatus;
import com.banking.thejavabanking.domain.loans.dto.requests.LoanDetailRequest;
import com.banking.thejavabanking.domain.loans.entity.LoanDetail;
import com.banking.thejavabanking.domain.loans.entity.LoanInfo;
import com.banking.thejavabanking.domain.loans.entity.LoanPayment;
import com.banking.thejavabanking.domain.loans.repositories.LoanDetailRepository;
import com.banking.thejavabanking.domain.loans.services.ILoanDetailService;
import com.banking.thejavabanking.domain.users.entities.User;
import com.banking.thejavabanking.domain.users.services.impl.UserServiceImpl;
import com.banking.thejavabanking.infrastructure.utils.InterestUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.banking.thejavabanking.infrastructure.utils.InterestUtils.calculateMonthlyInterestPayment;
import static com.banking.thejavabanking.infrastructure.utils.InterestUtils.calculateMonthlyPrincipalPayment;

@Service
@RequiredArgsConstructor
@FieldDefaults(
        makeFinal = true,
        level = lombok.AccessLevel.PRIVATE
)
@Slf4j
public class LoanDetailServiceImpl implements ILoanDetailService {
    LoanDetailRepository loanDetailRepository;
    LoanInfoServiceImpl loanInfoService;
    UserServiceImpl userService;
    AccountServiceImpl accountService;
    AccountRepository accountRepository;

    @Override
    public List<LoanDetail> getLoanDetails() {
        return loanDetailRepository.findAll();
    }

    @Override
    public LoanDetail getLoanDetailById(Integer id) {
        return loanDetailRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Loan detail not found with id: " + id));
    }

    @Override
    public LoanDetail saveLoanDetail(LoanDetailRequest loanDetailRequest) {
//        if (isLoanDetailExists(loanDetailRequest.getUserId()))
//            throw new AppException(LOAN_DETAIL_EXISTS);

        LoanInfo loanInfo = loanDetailRequest.getLoanInfo();
        if (loanInfo == null) throw new RuntimeException("Loan info is required");

        loanInfoService.createLoanInfo(loanInfo);

        LoanDetail loanDetail = new LoanDetail();
        loanDetail.setReferenceNumber(loanDetailRequest.getReferenceNumber());
        loanDetail.setUser(userService.getUserById(loanDetailRequest.getUserId()));
        loanDetail.setLoanInfo(loanInfo);
        loanDetail.setLoanStatus(LoanStatus.PENDING);

        return loanDetailRepository.save(loanDetail);
    }

    @Override
    public void deleteLoanDetailById(Integer id) {
        loanDetailRepository.deleteById(id);
    }

    @Override
    public double calculateInterest(Integer id, Enums.InterestType type) {
        LoanDetail loanDetail = loanDetailRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Loan detail not found with id: " + id));

        LoanInfo loanInfo = loanDetail.getLoanInfo();

        return InterestUtils.calculateInterest(
                loanInfo.getLoanAmount(),
                loanInfo.getInterestRate(),
                loanInfo.getLoanTerm(),
                (type == Enums.InterestType.SIMPLE) ? 0 : 12,
                type != Enums.InterestType.SIMPLE
        );
    }

    @Override
    public void updateLoanDetail(
            LoanDetail loanDetail, Enums.LoanStatus newStatus
    ) {
        LoanDetail loanDetailToUpdate =
                loanDetailRepository.findById(loanDetail.getId()).orElseThrow(
                        () -> new RuntimeException("Loan detail not found with id: " + loanDetail.getId()));
        loanDetailToUpdate.setLoanStatus(newStatus);

        loanDetailRepository.save(loanDetailToUpdate);
    }

    public void updateLoanDetail(
            LoanDetail loanDetail, Enums.LoanStatus newStatus,
            LoanPaymentStatus newPaymentStatus
    ) {
        LoanDetail loanDetailToUpdate =
                loanDetailRepository.findById(loanDetail.getId()).orElseThrow(
                        () -> new RuntimeException("Loan detail not found with id: " + loanDetail.getId()));
        loanDetailToUpdate.setLoanStatus(newStatus);
        loanDetailToUpdate.setLoanPaymentStatus(newPaymentStatus);
        loanDetailRepository.save(loanDetailToUpdate);
    }

    // Total amount to pay monthly = base amount + interest amount (simple interest)
    @Override
    public InterestCalculationResponse finalMonthlyAmountIncludingInterest(Integer id) {
        LoanDetail loanDetail = loanDetailRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Loan detail not found with id: " + id));
        LoanInfo loanInfo = loanDetail.getLoanInfo();

        double baseAmount = calculateMonthlyPrincipalPayment(
                loanInfo.getLoanAmount(),
                loanInfo.getLoanTerm()
        );
        double interestAmount = calculateMonthlyInterestPayment(
                loanInfo.getLoanAmount(),
                loanInfo.getInterestRate(),
                loanInfo.getLoanTerm()
        );

        return new InterestCalculationResponse(baseAmount, interestAmount);
    }

    // Calculate interest for a given loan detail based on the interest type (simple or compound)
    // and return the total amount to pay monthly = base amount + interest amount
    // not yet implemented compound interest calculation
    private List<InterestCalculationResponse> calculatePaymentSchedule(
            double principal, double interestRate, double termInt, Enums.InterestType interestType
    ) {
        List<InterestCalculationResponse> result = new ArrayList<>();
        double totalInterestToPay = 0;
        double total = principal;
        double term = termInt;

        if (Enums.InterestType.SIMPLE == interestType) {
            for (int i = 1; i <= termInt; i++) {
                double monthlyPrincipal = calculateMonthlyPrincipalPayment(principal, term);
                double monthlyInterest = calculateMonthlyInterestPayment(
                        principal,
                        interestRate,
                        term
                );

                totalInterestToPay += monthlyInterest;
                total += monthlyInterest;

                result.add(new InterestCalculationResponse(
                        monthlyInterest,
                        totalInterestToPay,
                        total
                ));
            }
        }

        return result;
    }

    public List<InterestCalculationResponse> calculateDecreasedInterestMonthly(Integer id) {
        LoanDetail loanDetail = loanDetailRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Loan detail not found with id: " + id));
        LoanInfo loanInfo = loanDetail.getLoanInfo();

        return calculatePaymentSchedule(
                loanInfo.getLoanAmount(),
                loanInfo.getInterestRate(),
                loanInfo.getLoanTerm(),
                Enums.InterestType.SIMPLE
        );
    }

    // check if user paid for loan detail => if they will loan
//    boolean isLoanDetailExists(Integer id) {
//        return loanDetailRepository.existsByUserId(id);
//    }

    @Scheduled(fixedRate = 10000000)
    @Transactional
    public void updateLoanDetailStatus() {
        List<LoanDetail> loanDetails = loanDetailRepository.findAll();
        for (LoanDetail loanDetail : loanDetails) {
            if (loanDetail.getLoanStatus() == LoanStatus.APPROVED) {

                // check if the loan payment status is not paid
                if (loanDetail.getLoanPaymentStatus() == LoanPaymentStatus.PAID ||
                        checkLoanPaymentFull(loanDetail)) {
                    loanDetail.setLoanPaymentStatus(LoanPaymentStatus.PAID);
                    loanDetailRepository.save(loanDetail);
                } else {
                    //method payment for the loan is due for every month
                    paymentForLoan(loanDetail);
                }
            }
        }
    }

    public boolean checkLoanPaymentFull(LoanDetail loanDetail) {
        List<LoanPayment> loanPayment = loanDetail.getLoanPayments();
        double totalPaid = 0;
        for (LoanPayment payment : loanPayment)
            totalPaid += payment.getPayAmount();

        return totalPaid >= loanDetail.getLoanInfo().getLoanAmount();
    }

    public void paymentForLoan(LoanDetail loanDetail) {
        LoanPayment loanPayment = new LoanPayment();
        User user = loanDetail.getUser();

        LoanInfo loanInfo = loanDetail.getLoanInfo();

        double baseAmount = calculateMonthlyPrincipalPayment(
                loanInfo.getLoanAmount(),
                loanInfo.getLoanTerm()
        );

        double interestAmount = calculateMonthlyInterestPayment(
                loanInfo.getLoanAmount(),
                loanInfo.getInterestRate(),
                loanInfo.getLoanTerm()
        );

        BigDecimal totalAmount = BigDecimal.valueOf(baseAmount + interestAmount);

        // reduce the loan amount from the user account
        accountService.debitAccount(
                CreditDebitRequest.builder()
                                  .accountNumber(user.getAccount().getAccountNumber())
                                  .amount(totalAmount)
                                  .build()
        );

        accountRepository.updateBalance(
                user.getId(),
                user.getAccount().getBalance().subtract(totalAmount)
        );

        log.info(String.valueOf(user.getAccount().getBalance().subtract(totalAmount)));
        log.info(user.getAccount().getAccountNumber());

        loanPayment.setLoanDetail(loanDetail);
        loanPayment.setPayAmount(baseAmount + interestAmount);
        loanDetail.getLoanPayments().add(loanPayment);

        loanDetailRepository.save(loanDetail);
    }
}
