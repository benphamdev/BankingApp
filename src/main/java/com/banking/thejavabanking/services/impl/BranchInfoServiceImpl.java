package com.banking.thejavabanking.services.impl;

import com.banking.thejavabanking.dto.requests.BranchUpdateRequest;
import com.banking.thejavabanking.models.entity.BranchInfo;
import com.banking.thejavabanking.repositories.BranchInfoRepository;
import com.banking.thejavabanking.services.IBranchInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchInfoServiceImpl implements IBranchInfoService {
    private final BranchInfoRepository branchInfoRepository;

    @Autowired
    public BranchInfoServiceImpl(BranchInfoRepository branchInfoRepository) {
        this.branchInfoRepository = branchInfoRepository;
    }

    @Override
    public BranchInfo createBranchInfo(BranchInfo branchInfo) {
        return branchInfoRepository.save(branchInfo);
    }

    @Override
    public void deleteBranchInfo(int id) {
        branchInfoRepository.deleteById(id);
    }

    @Override
    public List<BranchInfo> getAllBranchInfo() {
        return branchInfoRepository.findAll();
    }

    @Override
    public Optional<BranchInfo> getBranchInfoById(int id) {
        return branchInfoRepository.findById(id);
    }

    @Override
    public List<BranchInfo> getBranchInfoByProvinceId(int provinceId) {
        return branchInfoRepository.findByProvinceId(provinceId);
    }

    @Override
    public void updateBranchInfo(int id, BranchUpdateRequest branchUpdateRequest) {
        BranchInfo branchInfo = branchInfoRepository.findById(id).orElseThrow();
        branchInfo.setBranchName(branchUpdateRequest.getBranchName());
        branchInfo.setAddress(branchUpdateRequest.getAddress());
        branchInfoRepository.save(branchInfo);
    }
}
