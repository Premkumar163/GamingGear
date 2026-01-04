package com.GamingGear.service;

import com.GamingGear.dto.CategoryRequestDTO;
import com.GamingGear.dto.CategoryResponseDTO;
import com.GamingGear.model.Category;
import com.GamingGear.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    // =========================
    // BULK CREATE CATEGORIES
    // =========================
    @Transactional
    public void createCategoriesBulk(List<CategoryRequestDTO> dtos) {

        Map<String, Category> tempMap = new HashMap<>();

        // 1️⃣ Save ROOT categories first
        for (CategoryRequestDTO dto : dtos) {
            if (dto.getParentTempId() == null) {
                Category category = modelMapper.map(dto, Category.class);
                category.setId(null); // Ensure new entity
                category.setLevel(1);

                categoryRepository.save(category);
                tempMap.put(dto.getTempId(), category);
            }
        }

        // 2️⃣ Save CHILD categories (handles unordered input)
        boolean pending;
        do {
            pending = false;
            for (CategoryRequestDTO dto : dtos) {
                if (dto.getParentTempId() != null && !tempMap.containsKey(dto.getTempId())) {
                    Category parent = tempMap.get(dto.getParentTempId());
                    if (parent != null) {
                        Category category = modelMapper.map(dto, Category.class);
                        category.setId(null); // Ensure new entity
                        category.setParentId(parent.getId());
                        category.setLevel(parent.getLevel() + 1);

                        categoryRepository.save(category);
                        tempMap.put(dto.getTempId(), category);
                    } else {
                        pending = true; // Parent not yet saved, try next loop
                    }
                }
            }
        } while (pending);
    }

    // =========================
    // BREADCRUMB
    // =========================
    public List<CategoryResponseDTO> getBreadcrumb(Long categoryId) {

        List<CategoryResponseDTO> breadcrumb = new ArrayList<>();

        Category current = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        while (current != null) {
            breadcrumb.add(modelMapper.map(current, CategoryResponseDTO.class));

            current = (current.getParentId() == null)
                    ? null
                    : categoryRepository.findById(current.getParentId()).orElse(null);
        }

        Collections.reverse(breadcrumb);
        return breadcrumb;
    }}