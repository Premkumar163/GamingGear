package com.GamingGear.controller;



import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.GamingGear.dto.BrandRequestDto;
import com.GamingGear.service.BrandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    // 1️⃣ BULK CREATE BRANDS
    @PostMapping("/bulk")
    public String createBrandsBulk(@RequestBody List<BrandRequestDto> dtos) {
        brandService.createBrandsBulk(dtos);
        return "Brands created successfully";
    }

    // 2️⃣ GET ALL BRANDS
    @GetMapping
    public List<BrandRequestDto> getAllBrands() {
        return brandService.getAllBrands();
    }
}
