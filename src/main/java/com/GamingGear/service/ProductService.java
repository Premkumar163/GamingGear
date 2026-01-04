package com.GamingGear.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.GamingGear.dto.ProductRequestDto;
import com.GamingGear.dto.ProductResponseDto;
import com.GamingGear.model.Product;
import com.GamingGear.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 1️⃣ CREATE PRODUCT → SET isActive = true
    
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        Product product = modelMapper.map(dto, Product.class);
        product.setIsActive(true);   // ✅ set active

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductResponseDto.class);
    }
    
    // 2️⃣ GET PRODUCT BY SLUG
    public ProductResponseDto getProductBySlug(String slug) {

        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return modelMapper.map(product, ProductResponseDto.class);
    }

}
