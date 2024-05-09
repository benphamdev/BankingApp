package com.banking.thejavabanking.services;

import com.banking.thejavabanking.models.entity.Province;

import java.util.List;
import java.util.Optional;

public interface IProvinceService {
    List<Province> getAllProvinces();

    Optional<Province> getProvinceById(int id);

    Province createProvince(Province province);

    void deleteProvince(int id);
    
}
