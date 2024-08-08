package com.banking.thejavabanking.domain.accounts.services.impl;

import com.banking.thejavabanking.application.mapper.ProvinceMapper;
import com.banking.thejavabanking.domain.accounts.dto.responses.ProvinceResponse;
import com.banking.thejavabanking.domain.accounts.entity.Province;
import com.banking.thejavabanking.domain.accounts.repositories.ProvinceRepository;
import com.banking.thejavabanking.domain.accounts.services.IProvinceService;
import com.banking.thejavabanking.domain.common.constants.Enums;
import com.banking.thejavabanking.domain.common.constants.EnumsErrorResponse;
import com.banking.thejavabanking.domain.common.exceptions.AppException;
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
        return provinceRepository.findAll()
                                 .stream()
                                 .map(provinceMapper::toProvinceResponse)
                                 .collect(
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
            throw new AppException(EnumsErrorResponse.PROVINCE_NOT_FOUND);
        provinceRepository.deleteById(id);
    }
}
