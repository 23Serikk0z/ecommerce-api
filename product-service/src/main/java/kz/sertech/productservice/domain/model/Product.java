package kz.sertech.productservice.domain.model;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Product {

    @Id
    private UUID id;

    private String name;

    @Embedded
    private Money price;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    protected Product() {}

    public Product(UUID id, String name, Money price, Category category, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        validate();
    }

    public void update(String name, Money price, Category category, Integer quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        validate();
    }


    private void validate() {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
    }
}
