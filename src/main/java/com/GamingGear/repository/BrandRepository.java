package com.GamingGear.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GamingGear.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
