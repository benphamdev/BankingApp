package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.BranchInfoRequest;
import com.banking.thejavabanking.dto.requests.BranchUpdateRequest;
import com.banking.thejavabanking.dto.respones.BranchResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.mapper.BranchMapper;
import com.banking.thejavabanking.models.entity.BranchInfo;
import com.banking.thejavabanking.models.entity.Province;
import com.banking.thejavabanking.repositories.BranchInfoRepository;
import com.banking.thejavabanking.services.IBranchInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.banking.thejavabanking.exceptions.EnumsErrorResponse.PROVINCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BranchInfoServiceImpl implements IBranchInfoService {
    private final BranchMapper branchMapper;
    private final BranchInfoRepository branchInfoRepository;
    private final ProvinceServiceImpl provinceService;

    @Override
    public Integer createBranchInfo(BranchInfoRequest branchInfoRequest) {

        Optional<Province> province = provinceService.getProvinceByName(branchInfoRequest.getProvinceName());
        if (province.isEmpty())
            throw new AppException(PROVINCE_NOT_FOUND);
        BranchInfo branchInfo = new BranchInfo();
        branchInfo.setBranchName(branchInfoRequest.getBranchName());
        branchInfo.setAddress(branchInfoRequest.getAddress());
        branchInfo.setProvince(province.get());

        branchInfo.getProvince()
                  .setName(province.get()
                                   .getName());
        branchInfoRepository.save(branchInfo);
        return branchInfo.getId();
    }

    @Override
    public void deleteBranchInfo(Integer id) {
        branchInfoRepository.deleteBranchInfoById(id);
    }

    @Override
    public List<BranchResponse> getAllBranchInfo() {
        return branchInfoRepository.findAll()
                                   .stream()
                                   .map(branchMapper::toBranchResponse)
                                   .toList();
    }

    @Override
    public Optional<BranchInfo> getBranchInfoById(Integer id) {
        return branchInfoRepository.findById(id);
    }

    @Override
    public List<BranchInfo> getBranchInfoByProvinceId(Integer provinceId) {
        if (provinceService.getProvinceById(provinceId)
                           .isEmpty())
            throw new AppException(PROVINCE_NOT_FOUND);

        return branchInfoRepository.findByProvinceId(provinceId);
    }

    @Override
    public void updateBranchInfo(Integer id, BranchUpdateRequest branchUpdateRequest) {
        BranchInfo branchInfo = branchInfoRepository.findById(id)
                                                    .orElseThrow();
        branchInfo.setBranchName(branchUpdateRequest.getBranchName());
        branchInfo.setAddress(branchUpdateRequest.getAddress());
        branchInfoRepository.save(branchInfo);
    }
}
