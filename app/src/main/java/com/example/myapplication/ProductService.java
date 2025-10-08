package com.example.myapplication;

import android.content.Context;

import java.util.List;

public class ProductService {
    private final ProductRepository repo;

    public ProductService(Context context) {
        this.repo = new ProductRepository(context);
    }

    public long createProduct(String name, String description, double price, int quantity) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (price < 0) throw new IllegalArgumentException("price must be >= 0");
        if (quantity < 0) throw new IllegalArgumentException("quantity must be >= 0");

        // tạo Product với id tạm (0) rồi insert, sau đó gán id trả về cho object nếu cần
        Product p = new Product(0, name.trim(), description == null ? "" : description.trim(), price, quantity);
        long id = repo.insert(p);
        if (id > 0) p.setId(id);
        return id;
    }

    public Product getProduct(long id) {
        if (id <= 0) return null;
        return repo.getById(id);
    }

    public List<Product> listProducts() {
        return repo.getAll();
    }

    public boolean updateProduct(long id, String name, String description, double price, int quantity) {
        if (id <= 0) throw new IllegalArgumentException("invalid id");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (price < 0) throw new IllegalArgumentException("price must be >= 0");
        if (quantity < 0) throw new IllegalArgumentException("quantity must be >= 0");
        Product p = new Product(id, name.trim(), description == null ? "" : description.trim(), price, quantity);
        int rows = repo.update(p);
        return rows > 0;
    }

    public boolean deleteProduct(long id) {
        if (id <= 0) throw new IllegalArgumentException("invalid id");
        int rows = repo.delete(id);
        return rows > 0;
    }
}
