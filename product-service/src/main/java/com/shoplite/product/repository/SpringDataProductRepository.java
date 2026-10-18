package com.shoplite.product.repository;

import com.shoplite.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
}
