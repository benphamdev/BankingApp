package com.banking.thejavabanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TheJavaBankingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TheJavaBankingApplication.class, args);
    }
}
