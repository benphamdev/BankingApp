package com.banking.thejavabanking.dto.respones;

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
