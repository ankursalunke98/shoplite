package com.shoplite.product.repository;

import com.shoplite.product.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateProductRepository implements ProductRepository{

    private static final String COLUMNS = "id, name, price, stock, created_at, updated_at";

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getBigDecimal("price"),
            rs.getInt("stock"),
            rs.getObject("created_at", OffsetDateTime.class),
            rs.getObject("updated_at", OffsetDateTime.class)
    );

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateProductRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO products (name, price, stock) VALUES (?,?,?) RETURNING " + COLUMNS;
        return jdbcTemplate.queryForObject(sql, PRODUCT_ROW_MAPPER, product.getName(), product.getPrice(), product.getStock());
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT " + COLUMNS + " FROM products WHERE id = ?";
        List<Product> result = jdbcTemplate.query(sql, PRODUCT_ROW_MAPPER, id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT " + COLUMNS + " FROM products ORDER BY id";
        return jdbcTemplate.query(sql, PRODUCT_ROW_MAPPER);
    }

    @Override
    public List<Product> findByName(String name) {
        String sql = "SELECT " + COLUMNS + " FROM products WHERE name = ?";
        return jdbcTemplate.query(sql, PRODUCT_ROW_MAPPER, name);
    }

    @Override
    public Optional<Product> update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ?, updated_at = now() " + "WHERE id = ? RETURNING " + COLUMNS;
        List<Product> result = jdbcTemplate.query(sql, PRODUCT_ROW_MAPPER, product.getName(), product.getPrice(), product.getStock(), product.getId());
        if (result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}
