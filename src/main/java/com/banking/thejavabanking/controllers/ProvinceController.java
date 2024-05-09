package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.models.entity.Province;
import com.banking.thejavabanking.services.impl.ProvinceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.banking.thejavabanking.exceptions.ErrorResponse.PROVINCE_EXISTS;
import static com.banking.thejavabanking.exceptions.ErrorResponse.PROVINCE_NOT_FOUND;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    private final ProvinceServiceImpl provinceService;

    @Autowired
    public ProvinceController(ProvinceServiceImpl provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping("/create")
    public BaseResponse<Province> createProvince(
            @RequestBody Province province
    ) {
        Province ans = provinceService.createProvince(province);
        if (ans == null)
            throw new AppException(PROVINCE_EXISTS);
        return BaseResponse.<Province>builder()
                           .data(ans)
                           .build();
    }

    @GetMapping
    public ResponseEntity<List<Province>> getAllProvinces() {
        List<Province> provinces = provinceService.getAllProvinces();
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/{id}")
    public BaseResponse<Province> getProvinceById(
            @PathVariable int id
    ) {
        Optional<Province> province = provinceService.getProvinceById(id);
        if (province.isEmpty())
            throw new AppException(PROVINCE_NOT_FOUND);
        return BaseResponse.<Province>builder()
                           .data(province.get())
                           .build();
    }

    // it is not necessary
    @PutMapping("/{id}")
    public BaseResponse<Void> updateProvince(
            @PathVariable int id,
            @RequestBody Province province
    ) {
        province.setId(id);
        provinceService.createProvince(province);
        return BaseResponse.<Void>builder()
                           .message("Update province successfully")
                           .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteProvince(
            @PathVariable int id
    ) {
        provinceService.deleteProvince(id);
        return BaseResponse.<Void>builder()
                           .message("Delete province successfully")
                           .build();
    }
}
