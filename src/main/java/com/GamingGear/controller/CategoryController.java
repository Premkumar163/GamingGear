package com.GamingGear.controller;

import com.GamingGear.dto.CategoryRequestDTO;
import com.GamingGear.dto.CategoryResponseDTO;
import com.GamingGear.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/bulk")
    public String createCategories(
            @RequestBody List<CategoryRequestDTO> requests) {
       
        		categoryService.createCategoriesBulk(requests);
        		
        		 return " Categories created Sucessfully";
    }

    @GetMapping("/breadcrumb/{categoryId}")
    public List<CategoryResponseDTO> getBreadcrumb(
            @PathVariable Long categoryId) {
        return categoryService.getBreadcrumb(categoryId);
    }

}
