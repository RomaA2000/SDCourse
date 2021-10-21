package ru.akirakozov.sd.refactoring.product;

import java.util.Objects;

public class Product {
    public final String name;
    public final long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return (this == obj) || (price == product.price && Objects.equals(name, product.name));
    }
}