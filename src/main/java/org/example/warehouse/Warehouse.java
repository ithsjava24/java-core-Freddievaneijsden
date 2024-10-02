package org.example.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class Warehouse {
    private String name;
    private final List<ProductRecord> productRecords = new ArrayList<>();
    private final List <ProductRecord> changedProductRecords = new ArrayList<>();

    private Warehouse() {
    }

    private Warehouse(String warehouseName) {
        this.name = warehouseName;
    }

    public static Warehouse getInstance (String warehouseName) {
        return new Warehouse(warehouseName);
    }

    public static Warehouse getInstance () {
        return new Warehouse();
    }

    public boolean isEmpty() {
        return this.name == null;
}

    public List<ProductRecord> getProducts () {
        return productRecords;
    }

    public List<ProductRecord> getChangedProductRecords () {
        return changedProductRecords;
    }

    public ProductRecord addProduct(UUID UUID_value, String name, Category categoryName, BigDecimal bigDecimal) {
        for (ProductRecord productRecord : productRecords) {
            if (productRecord.uuid().equals(UUID_value)) {
                throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates");
            }
        }
        var product = new ProductRecord(UUID_value, name, categoryName, bigDecimal);
        productRecords.add(product);
        return product;
    }

    public Optional<ProductRecord> getProductById(UUID UUID_value) {
            for (ProductRecord product : productRecords) {
                if (product.uuid().equals(UUID_value)) {
                    return Optional.of(product);
                }
            }
            return Optional.empty();
        }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        for (ProductRecord product : productRecords) {
            if (product.UUID_value().equals(uuid)) {
                changedProductRecords.add(product);
            }
        }
        productRecords.stream()
                .filter(product -> product.UUID_value().equals(uuid))
                .forEach(product -> product.setBigDecimal(newPrice));
    }

    public List<ProductRecord> getChangedProducts() {
        return changedProductRecords;
    }
}
