package org.example.warehouse;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public final class ProductRecord {
    private final UUID UUID_value;
    private final String UUID_name;
    private final Category categoryName;
    private  BigDecimal bigDecimal;

    public ProductRecord(UUID UUID_value, String UUID_name, Category categoryName, BigDecimal bigDecimal) {
        this.UUID_value = UUID_value;
        this.UUID_name = UUID_name;
        this.categoryName = categoryName;
        this.bigDecimal = bigDecimal;
    }

    public UUID uuid() {
        if (UUID_value == null) {
            return UUID.randomUUID();
        }
        return UUID_value;
    }


    public BigDecimal price() {
        if (bigDecimal == null) {
            return new BigDecimal(0);
        }
        return bigDecimal;
    }

    public UUID UUID_value() {
        return UUID_value;
    }

    public String UUID_name() {
        return UUID_name;
    }

    public Category categoryName() {
        return categoryName;
    }

    public BigDecimal bigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ProductRecord) obj;
        return Objects.equals(this.UUID_value, that.UUID_value) &&
                Objects.equals(this.UUID_name, that.UUID_name) &&
                Objects.equals(this.categoryName, that.categoryName) &&
                Objects.equals(this.bigDecimal, that.bigDecimal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UUID_value, UUID_name, categoryName, bigDecimal);
    }

    @Override
    public String toString() {
        return "ProductRecord[" +
                "UUID_value=" + UUID_value + ", " +
                "UUID_name=" + UUID_name + ", " +
                "categoryName=" + categoryName + ", " +
                "bigDecimal=" + bigDecimal + ']';
    }


}
