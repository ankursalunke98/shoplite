package com.shoplite.product.repository;

import com.shoplite.product.domain.Product;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepository implements ProductRepository{

    private static final String COLUMNS = "id, name, price, stock, created_at, updated_at";

    private final DataSource dataSource;

    public JdbcProductRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Product mapRow(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getBigDecimal("price"),
                resultSet.getInt("stock"),
                resultSet.getObject("created_at", OffsetDateTime.class),
                resultSet.getObject("updated_at", OffsetDateTime.class)
        );
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?) RETURNING " + COLUMNS;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setInt(3, product.getStock());
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to create product", e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT " + COLUMNS + " FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find product " + id, e);
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT " + COLUMNS + " FROM products ORDER BY id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(mapRow(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to list products", e);
        }
    }

    @Override
    public List<Product> findByName(String name) {
        String sql = "SELECT " + COLUMNS + " FROM products WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    products.add(mapRow(resultSet));
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to search products by name", e);
        }
    }

    @Override
    public Optional<Product> update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ?, updated_at = now() "
                + "WHERE id = ? RETURNING " + COLUMNS;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setLong(4, product.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update product " + product.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to delete product " + id, e);
        }
    }
}

