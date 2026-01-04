package com.GamingGear.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GamingGear.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);
}
