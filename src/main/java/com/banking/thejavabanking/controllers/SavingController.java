package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.requests.SavingRequest;
import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.services.ISavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saving")
public class SavingController {
    @Autowired
    private ISavingService savingService;

    @PostMapping("/create")
    public BaseResponse<String> createSavingAccount(
            @RequestBody SavingRequest savingRequest
    ) {
        savingService.createSavingAccount(savingRequest);
        return new BaseResponse<>("Saving account created successfully");
    }

    @PostMapping("/cancel/{savingId}")
    public BaseResponse<String> cancelSavingAccount(
            @PathVariable int savingId
    ) {
        savingService.cancelSaving(savingId);
        return new BaseResponse<>("Saving account canceled successfully");
    }
}