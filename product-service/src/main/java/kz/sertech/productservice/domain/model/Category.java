package kz.sertech.productservice.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Category {


    @Id
    private UUID id;

    private String name;

    protected Category() {}

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
        validate();
    }

    private void validate() {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
    }

    public void update (String name){
        this.name = name;
        validate();
    }
}
