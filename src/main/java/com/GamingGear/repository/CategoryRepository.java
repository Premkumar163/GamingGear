package com.GamingGear.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GamingGear.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

   
    // Sub-categories of a parent
    List<Category> findByParentId(Long parentId);

    // Root / main categories (parentId IS NULL)
    List<Category> findByParentIdIsNull();

}
