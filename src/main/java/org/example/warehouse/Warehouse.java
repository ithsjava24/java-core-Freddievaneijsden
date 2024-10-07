package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;

public class Warehouse {
    private String name;
    private final List<ProductRecord> addedProducts = new ArrayList<>();
    private final List<ProductRecord> changedProductRecords = new ArrayList<>();
    private static final Map <String, Warehouse> warehouses = new HashMap <>();

    private Warehouse() {
    }

    private Warehouse(String warehouseName) {
        this.name = warehouseName;
    }

    public static Warehouse getInstance(String warehouseName) {
        if (!warehouses.containsKey(warehouseName)) {
            warehouses.put(warehouseName, new Warehouse(warehouseName));
        }

        return warehouses.get(warehouseName);
    }

    public static Warehouse getInstance() {
        return new Warehouse();
    }

    public boolean isEmpty() {
        return this.addedProducts.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return List.copyOf(addedProducts);
    }

    public List<ProductRecord> getChangedProductRecords() {
        return changedProductRecords;
    }

    public ProductRecord addProduct(UUID UUID_value, String name, Category categoryName, BigDecimal price) {
        for (ProductRecord productRecord : addedProducts) {
            if (productRecord.uuid().equals(UUID_value)) {
                throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
            }
        }
        var product = new ProductRecord(UUID_value, name, categoryName, price);
        addedProducts.add(product);
        return product;
    }

    public Optional<ProductRecord> getProductById(UUID UUID_value) {
        for (ProductRecord product : addedProducts) {
            if (product.uuid().equals(UUID_value)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        for (ProductRecord product : addedProducts) {
            if (product.UUID_value().equals(uuid)) {
                changedProductRecords.add(product);
            }
        }

        boolean containsUUID = addedProducts.stream().anyMatch(product -> product.UUID_value().equals(uuid));
        if (!containsUUID) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }

        addedProducts.stream()
                .filter(product -> product.UUID_value().equals(uuid))
                .forEach(product -> product.setPrice(newPrice));
    }

    public List<ProductRecord> getChangedProducts() {
        return changedProductRecords;
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> groupedByCategories = new HashMap<>();
        for (ProductRecord productRecord : addedProducts) {
            groupedByCategories.computeIfAbsent(productRecord.category(), k -> new ArrayList<>());
            groupedByCategories.get(productRecord.category()).add(productRecord);
        }
        return groupedByCategories;
    }

    public List<ProductRecord> getProductsBy(Category category) {
        List<ProductRecord> productRecords = List.of();
        if (getProductsGroupedByCategories().containsKey(category)) {
            productRecords = getProductsGroupedByCategories().get(category);
        }
        return productRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Warehouse warehouse)) return false;
        return Objects.equals(name, warehouse.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
