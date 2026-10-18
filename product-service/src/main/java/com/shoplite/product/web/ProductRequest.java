package com.shoplite.product.web;

import java.math.BigDecimal;

public record ProductRequest(String name, BigDecimal price, int stock) {
}
