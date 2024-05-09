package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.models.Enums;
import com.banking.thejavabanking.models.entity.Province;
import com.banking.thejavabanking.repositories.ProvinceRepository;
import com.banking.thejavabanking.services.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceServiceImpl implements IProvinceService {
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public Optional<Province> getProvinceById(int id) {
        return provinceRepository.findById(id);
    }

    @Override
    public Province createProvince(Province province) {
        // check province name exists
        Enums.Provinces name = province.getName();
        if (provinceRepository.existsByName(name)) {
            return null;
        }
        return provinceRepository.save(province);
    }

    @Override
    public void deleteProvince(int id) {
        provinceRepository.deleteById(id);
    }
}
