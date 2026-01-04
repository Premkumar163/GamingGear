package com.GamingGear.controller;

import org.springframework.web.bind.annotation.*;

import com.GamingGear.dto.ProductRequestDto;
import com.GamingGear.dto.ProductResponseDto;
import com.GamingGear.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 1️⃣ CREATE PRODUCT
    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto dto) {
        return productService.createProduct(dto);
    }

    // 2️⃣ GET PRODUCT BY SLUG
    @GetMapping("/{slug}")
    public ProductResponseDto getProductBySlug(@PathVariable String slug) {
        return productService.getProductBySlug(slug);
    }
}
