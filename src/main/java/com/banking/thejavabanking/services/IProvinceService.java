package com.banking.thejavabanking.services;

import com.banking.thejavabanking.dto.respones.ProvinceResponse;
import com.banking.thejavabanking.models.entity.Province;

import java.util.List;
import java.util.Optional;

public interface IProvinceService {
    List<ProvinceResponse> getAllProvinces();

    Optional<Province> getProvinceById(Integer id);

    Optional<Province> getProvinceByName(String name);

    Integer createProvince(Province province);

    void deleteProvince(Integer id);
}
