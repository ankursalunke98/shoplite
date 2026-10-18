package com.shoplite.product.repository;

import com.shoplite.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByName(String name);
    Optional<Product> update(Product product);
    boolean deleteById(Long id);
}
