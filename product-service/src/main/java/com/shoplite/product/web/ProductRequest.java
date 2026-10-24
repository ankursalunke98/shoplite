package com.shoplite.product.web;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "name is required")
        @Size(max = 200, message = "name must be at most 200 characters")
        String name,

        @NotNull(message = "price is required")
        @PositiveOrZero(message = "price must be positive or zero")
        @Digits(integer = 10, fraction = 2, message = "the price must have at most 10 digits or 2 decimal places")
        BigDecimal price,

        @NotNull(message = "stock is required")
        @PositiveOrZero(message = "stock must be zero or more")
        Integer stock
) {
}
