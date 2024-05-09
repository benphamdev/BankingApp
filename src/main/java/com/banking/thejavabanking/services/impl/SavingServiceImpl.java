package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.SavingRequest;
import com.banking.thejavabanking.models.entity.Account;
import com.banking.thejavabanking.models.entity.Saving;
import com.banking.thejavabanking.repositories.AccountRepository;
import com.banking.thejavabanking.repositories.SavingRepository;
import com.banking.thejavabanking.repositories.UserRepository;
import com.banking.thejavabanking.services.ISavingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(
        makeFinal = true,
        level = AccessLevel.PRIVATE
)
public class SavingServiceImpl implements ISavingService {
    SavingRepository savingRepository;
    AccountRepository accountRepository;
    UserRepository userRepository;
    AccountServiceImpl accountService;

    @Override
    public void createSavingAccount(SavingRequest savingRequest) {

        // update account balance
        Account account = accountService.getAccountByUserId(savingRequest.getUserId())
                                        .orElseThrow(() -> new RuntimeException(
                                                "Account not found"));

        if (account.getBalance().compareTo(savingRequest.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        accountRepository.updateBalance(
                savingRequest.getUserId(),
                account.getBalance().subtract(savingRequest.getAmount())
        );

        Saving saving = Saving.builder()
                              .userId(savingRequest.getUserId())
                              .baseAmount(savingRequest.getAmount())
                              .refundAmount(savingRequest.getAmount())
                              .duration(savingRequest.getDuration())
                              .maturationDate(
                                      LocalDateTime.now()
                                                   .plus(
                                                           savingRequest.getDuration(),
                                                           ChronoUnit.MINUTES
                                                   ))
                              .build();

        savingRepository.save(saving);
    }

    @Scheduled(fixedRate = 1000000000)
    @Override
    public void processMaturedSavings() {
        List<Saving> maturedSavings = savingRepository.findAll();
        maturedSavings.forEach(saving -> {
            if (!LocalDateTime.now()
                              .isBefore(saving.getMaturationDate()) || saving.isStatusRefund()) {
                refundSaving(
                        saving.getId(),
                        saving.getUserId(),
                        saving.getRefundAmount(),
                        saving.isStatusRefund()
                );
            } else {
                processSaving(saving);
            }
        });
    }

    public void processSaving(Saving saving) {
        Optional<Saving> optionalSaving = savingRepository.findById(saving.getId());

        BigDecimal balance = saving.getRefundAmount().add(
                saving.getRefundAmount().multiply(BigDecimal.valueOf(0.2)));

        try {
            savingRepository.updateAmount(saving.getId(), balance);
            log.info("Updated amount for saving id: " + saving.getId());
        } catch (Exception e) {
            System.out.println("Failed to update amount for saving id: " + saving.getId());
            e.printStackTrace();
        }
    }

    @Override
    public void cancelSaving(int savingId) {
        Saving saving = savingRepository.findById(savingId)
                                        .orElseThrow(() -> new RuntimeException("Saving not found"));
        refundSaving(
                savingId,
                saving.getUserId(),
                saving.getBaseAmount().multiply(BigDecimal.valueOf(0.1)),
                saving.isStatusRefund()
        );
    }

    public void refundSaving(int id, int userId, BigDecimal refundAmount, boolean statusRefund) {
        if (statusRefund) {
            return;
        }
        Optional<Saving> optionalSaving = savingRepository.findById(id);

        savingRepository.updateStatusRefund(id, true);

        log.info("Saving id: " + id + " is matured");

        // update  account balance
        Account account = accountService.getAccountByUserId(userId)
                                        .orElseThrow(() -> new RuntimeException(
                                                "Account not found"));

        accountRepository.updateBalance(
                userId,
                account.getBalance().add(refundAmount)
        );

        // send email
        log.info("Sending email to user id: " + userId + " for saving id: " + id);
    }
}
