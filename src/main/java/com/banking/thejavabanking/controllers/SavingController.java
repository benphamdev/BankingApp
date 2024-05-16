package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.SavingRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.models.entity.Saving;
import com.banking.thejavabanking.services.impl.SavingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saving")
public class SavingController {

    private final SavingServiceImpl savingService;

    @Autowired
    public SavingController(SavingServiceImpl savingService) {this.savingService = savingService;}

    @PostMapping("/create")
    public BaseResponse<Saving> createSavingAccount(
            @RequestBody SavingRequest savingRequest
    ) {
        return BaseResponse.<Saving>builder()
                           .message("Saving account created successfully")
                           .data(savingService.createSavingAccount(savingRequest))
                           .build();
    }

    @GetMapping("/cancel/{savingId}")
    public BaseResponse<Void> cancelSavingAccount(
            @PathVariable Integer savingId
    ) {
        savingService.cancelSaving(savingId);
        return BaseResponse.<Void>builder()
                           .message("Saving account cancelled successfully")
                           .build();
    }

    @GetMapping("/refund")
    public BaseResponse<?> refundSavingAccount() {
        return null;
    }

    @GetMapping("/{userId}")
    public BaseResponse<Saving> getSavingByUserId(
            @PathVariable Integer userId
    ) {
        return BaseResponse.<Saving>builder()
                           .message("Saving account retrieved successfully")
                           .data(savingService.getSavingByUserId(userId))
                           .build();
    }
}