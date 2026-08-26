package com.example.product_service.service;

import com.example.product_service.model.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final ConcurrentHashMap<Long, Product> products =
            new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    public Product createProduct(Product product) {

        Long id = idGenerator.getAndIncrement();

        Product newProduct = new Product(
                id,
                product.getName(),
                product.getQuantity(),
                product.getPrice()
        );

        products.put(id, newProduct);

        return newProduct;
    }

    public Product getProduct(Long id) {
        return products.get(id);
    }

    public Collection<Product> getProducts() {
        return products.values();
    }

    public synchronized boolean reserveInventory(
            Long productId,
            int quantity
    ) {

        Product product = products.get(productId);

        if (product == null) {
            return false;
        }

        if (product.getQuantity() < quantity) {
            return false;
        }

        product.setQuantity(
                product.getQuantity() - quantity
        );

        return true;
    }
}
