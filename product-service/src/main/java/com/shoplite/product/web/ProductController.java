package com.shoplite.product.web;

import com.shoplite.product.domain.Product;
import com.shoplite.product.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductRequest request){
        Product toSave = new Product(null, request.name(), request.price(), request.stock(), null, null);
        Product saved = productRepository.save(toSave);
        return ResponseEntity.created(URI.create("/api/v1/products" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return ResponseEntity.of(productRepository.findById(id));
    }

    @GetMapping
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String name){
        return productRepository.findByName(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductRequest request){
        Product toUpdate = new Product(id, request.name(), request.price(), request.stock(), null, null);
        return ResponseEntity.of(productRepository.update(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (productRepository.deleteById(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
