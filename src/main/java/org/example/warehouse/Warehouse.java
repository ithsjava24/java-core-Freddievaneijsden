package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private String name;
    private final List<ProductRecord> addedProducts = new ArrayList<>();
    private final List<ProductRecord> changedProductRecords = new ArrayList<>();
    private static final Map<String, Warehouse> warehouses = new HashMap<>();

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
        addedProducts.stream()
                .filter(product -> product.uuid().equals(UUID_value))
                .findAny()
                .ifPresent(product -> {
                    throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
                });

        ProductRecord newProduct = new ProductRecord(UUID_value, name, categoryName, price);
        addedProducts.add(newProduct);
        return newProduct;
    }

    public Optional<ProductRecord> getProductById(UUID UUID_value) {
        return addedProducts.stream()
                .filter(product -> product.uuid().equals(UUID_value))
                .findAny();
    }

    public void updateProductPrice(UUID UUID_value, BigDecimal newPrice) {
        addedProducts.stream()
                .filter(product -> product.UUID_value().equals(UUID_value))
                .findFirst()
                .ifPresentOrElse(
                        product -> {
                            changedProductRecords.add(product);
                            product.setPrice(newPrice);
                        },
                        () -> {
                            throw new IllegalArgumentException("Product with that id doesn't exist.");
                        }
                );

    }

    public List<ProductRecord> getChangedProducts() {
        return changedProductRecords;
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return addedProducts.stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
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
