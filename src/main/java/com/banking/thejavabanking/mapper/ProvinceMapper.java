package com.banking.thejavabanking.mapper;

import com.banking.thejavabanking.dto.respones.ProvinceResponse;
import com.banking.thejavabanking.models.entity.Province;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {
    ProvinceResponse toProvinceResponse(Province province);
}
