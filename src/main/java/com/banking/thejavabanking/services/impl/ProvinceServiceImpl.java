package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.respones.ProvinceResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.exceptions.ErrorResponse;
import com.banking.thejavabanking.mapper.ProvinceMapper;
import com.banking.thejavabanking.models.Enums;
import com.banking.thejavabanking.models.entity.Province;
import com.banking.thejavabanking.repositories.ProvinceRepository;
import com.banking.thejavabanking.services.IProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements IProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    @Override
    public List<ProvinceResponse> getAllProvinces() {
        return provinceRepository.findAll().stream().map(provinceMapper::toProvinceResponse).collect(
                Collectors.toList());
    }

    @Override
    public Optional<Province> getProvinceById(Integer id) {
        return provinceRepository.findById(id);
    }

    @Override
    public Optional<Province> getProvinceByName(String name) {
        return provinceRepository.findByName(Enums.Provinces.valueOf(name));
    }

    @Override
    public Integer createProvince(Province province) {
        // check province name exists
        Enums.Provinces name = province.getName();
        if (provinceRepository.existsByName(name)) {
            return null;
        }
        provinceRepository.save(province);
        return province.getId();
    }

    @Override
    public void deleteProvince(Integer id) {
        if (!provinceRepository.existsById(id))
            throw new AppException(ErrorResponse.PROVINCE_NOT_FOUND);
        provinceRepository.deleteById(id);
    }
}
