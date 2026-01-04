package com.GamingGear.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String slug;

    // For parent-child category hierarchy
    private Long parentId;

    // Category depth level (0 = main, 1 = sub, 2 = child)
    private Integer level;

    private Boolean isActive = true;
}
