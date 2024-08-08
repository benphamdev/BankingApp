package com.banking.thejavabanking.api;

import com.banking.thejavabanking.domain.notifications.dto.requests.OtpRequest;
import com.banking.thejavabanking.domain.notifications.dto.requests.OtpValidationRequest;
import com.banking.thejavabanking.domain.notifications.dto.responses.OtpResponseDto;
import com.banking.thejavabanking.domain.notifications.services.impl.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
@Slf4j
public class OtpController {
    private final SmsService smsService;

    public OtpController(SmsService smsService) {this.smsService = smsService;}

    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }

    @PostMapping("/send-otp")
    public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
        log.info("inside sendOtp :: {}", otpRequest.getUsername());
        return smsService.sendSMS(otpRequest);
    }

    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        log.info(
                "inside validateOtp :: {} {}",
                otpValidationRequest.getUsername(),
                otpValidationRequest.getOtpNumber()
        );
        return smsService.validateOtp(otpValidationRequest);
    }
}
