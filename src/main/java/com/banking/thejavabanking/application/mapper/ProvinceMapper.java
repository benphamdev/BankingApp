package com.banking.thejavabanking.application.mapper;

import com.banking.thejavabanking.domain.accounts.dto.responses.ProvinceResponse;
import com.banking.thejavabanking.domain.accounts.entity.Province;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {
    ProvinceResponse toProvinceResponse(Province province);
}
