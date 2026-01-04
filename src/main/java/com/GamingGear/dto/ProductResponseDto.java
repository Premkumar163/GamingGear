package com.GamingGear.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private Long categoryId;
    private Long brandId;
    private Boolean isActive;
    private LocalDate createdAt;
}
