package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.configuration.TwilioConfig;
import com.banking.thejavabanking.dto.requests.OtpRequest;
import com.banking.thejavabanking.dto.requests.OtpValidationRequest;
import com.banking.thejavabanking.dto.respones.OtpResponseDto;
import com.banking.thejavabanking.models.Enums;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
@Slf4j
public class SmsService {

    Map<String, String> otpMap = new HashMap<>();
    @Autowired
    private TwilioConfig twilioConfig;

    public OtpResponseDto sendSMS(OtpRequest otpRequest) {
        OtpResponseDto otpResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
            String otp = generateOTP();
            String otpMessage =
                    "Dear Customer , Your OTP is  " + otp + " for sending sms through Spring boot application. Thank You.";
            Message message = Message
                    .creator(to, from,
                             otpMessage
                    )
                    .create();
            otpMap.put(otpRequest.getUsername(), otp);
            otpResponseDto = new OtpResponseDto(Enums.OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(Enums.OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

    public String validateOtp(OtpValidationRequest otpValidationRequest) {
        Set<String> keys = otpMap.keySet();
        String username = null;
        for (String key : keys)
            username = key;
        if (otpValidationRequest.getUsername().equals(username)) {
            otpMap.remove(username, otpValidationRequest.getOtpNumber());
            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

}