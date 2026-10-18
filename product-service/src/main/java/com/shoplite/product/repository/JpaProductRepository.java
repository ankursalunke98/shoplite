package com.shoplite.product.repository;

import com.shoplite.product.domain.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JpaProductRepository implements ProductRepository {

    private final SpringDataProductRepository springDataRepository;

    public JpaProductRepository(SpringDataProductRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Product save(Product product) {
        return springDataRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springDataRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return springDataRepository.findAll(Sort.by("id"));
    }

    @Override
    public List<Product> findByName(String name) {
        return springDataRepository.findByName(name);
    }

    @Override
    public Optional<Product> update(Product product) {
        Optional<Product> existing = springDataRepository.findById(product.getId());
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Product entity = existing.get();
        entity.updateDetails(product.getName(), product.getPrice(), product.getStock());
        return Optional.of(springDataRepository.save(entity));
    }

    @Override
    public boolean deleteById(Long id) {
        if (!springDataRepository.existsById(id)) {
            return false;
        }
        springDataRepository.deleteById(id);
        return true;
    }
}