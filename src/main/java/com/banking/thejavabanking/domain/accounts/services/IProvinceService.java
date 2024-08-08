package com.banking.thejavabanking.domain.accounts.services;

import com.banking.thejavabanking.domain.accounts.dto.responses.ProvinceResponse;
import com.banking.thejavabanking.domain.accounts.entity.Province;

import java.util.List;
import java.util.Optional;

public interface IProvinceService {
    List<ProvinceResponse> getAllProvinces();

    Optional<Province> getProvinceById(Integer id);

    Optional<Province> getProvinceByName(String name);

    Integer createProvince(Province province);

    void deleteProvince(Integer id);
}
