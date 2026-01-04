package com.GamingGear.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.GamingGear.dto.BrandRequestDto;
import com.GamingGear.model.Brand;
import com.GamingGear.repository.BrandRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    // 1️⃣ BULK CREATE BRANDS
    public void createBrandsBulk(List<BrandRequestDto> dtos) {

        brandRepository.saveAll(
                dtos.stream()
                        .map(dto -> modelMapper.map(dto, Brand.class))
                        .collect(Collectors.toList())
        );
    }

    // 2️⃣ GET ALL ACTIVE BRANDS
    public List<BrandRequestDto> getAllBrands() {

        return brandRepository.findAll()
                .stream()
                .map(brand -> modelMapper.map(brand, BrandRequestDto.class))
                .collect(Collectors.toList());
    }

}