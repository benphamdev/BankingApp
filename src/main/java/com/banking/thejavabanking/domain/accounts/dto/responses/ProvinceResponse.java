package com.banking.thejavabanking.domain.accounts.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProvinceResponse {
    private Integer id;
    private String name;
}
