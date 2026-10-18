package com.shoplite.product.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private int stock;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Product(Long id, String name, BigDecimal price, int stock, OffsetDateTime createdAt, OffsetDateTime updatedAt){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
