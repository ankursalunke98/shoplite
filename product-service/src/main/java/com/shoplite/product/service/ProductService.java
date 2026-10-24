package com.shoplite.product.service;

import com.shoplite.product.domain.Product;
import com.shoplite.product.repository.ProductRepository;
import com.shoplite.product.web.ProductRequest;
import com.shoplite.product.web.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse create(ProductRequest request){
        Product product = new Product(null, request.name().strip(), request.price(), request.stock(), null, null);
        return ProductResponse.from(productRepository.save(product));
    }

    public ProductResponse getById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ProductResponse.from(product);
    }

    public List<ProductResponse> getAll(){
        return toResponse(productRepository.findAll());
    }

    public List<ProductResponse> searchByName(String name){
        return toResponse(productRepository.findByName(name.strip()));
    }

    public ProductResponse update(Long id, ProductRequest request){
        Product changes = new Product(id, request.name().strip(), request.price(), request.stock(), null, null);

        Product updated = productRepository.update(changes).orElseThrow(() -> new ProductNotFoundException(id));

        return ProductResponse.from(updated);
    }

    public void delete(Long id){
        boolean deleted = productRepository.deleteById(id);
        if (!deleted){
            throw new ProductNotFoundException(id);
        }
    }

    public List<ProductResponse> toResponse(List<Product> products) {
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : products){
            responses.add(ProductResponse.from(product));
        }

        return responses;
    }
}
