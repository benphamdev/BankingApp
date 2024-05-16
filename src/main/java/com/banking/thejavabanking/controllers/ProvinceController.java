package com.banking.thejavabanking.controllers;

import com.banking.thejavabanking.dto.respones.BaseResponse;
import com.banking.thejavabanking.dto.respones.ProvinceResponse;
import com.banking.thejavabanking.exceptions.AppException;
import com.banking.thejavabanking.models.entity.Province;
import com.banking.thejavabanking.services.impl.ProvinceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Create a new province", description = "Create a new province")
    public BaseResponse<Integer> createProvince(
            @RequestBody Province province
    ) {
        Integer ans = provinceService.createProvince(province);
        if (ans == null)
            throw new AppException(PROVINCE_EXISTS);
        return BaseResponse.<Integer>builder()
                           .message("Create province successfully")
                           .data(ans)
                           .build();
    }

    //    @GetMapping
//    @Operation(summary = "Get all provinces", description = "Get all provinces")
//    public ResponseEntity<List<Province>> getAllProvinces() {
//        List<Province> provinces = provinceService.getAllProvinces();
//        return ResponseEntity.ok(provinces);
//    }

    @GetMapping
    @Operation(summary = "Get all provinces", description = "Get all provinces")
    public BaseResponse<List<ProvinceResponse>> getAllProvinces() {
        List<ProvinceResponse> provinces = provinceService.getAllProvinces();
        return BaseResponse.<List<ProvinceResponse>>builder()
                           .message("Get all provinces successfully")
                           .data(provinces)
                           .build();
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
