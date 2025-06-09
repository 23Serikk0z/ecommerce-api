package kz.sertech.productservice;

import kz.sertech.productservice.domain.model.Category;
import kz.sertech.productservice.domain.model.Money;
import kz.sertech.productservice.domain.model.Product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ProductServiceApplicationTests {

    @Test
    void shouldCreateProductWithValidData() {
        Category category = new Category(UUID.randomUUID(), "Electronics");

        UUID productId = UUID.randomUUID();
        Money price = new Money(new BigDecimal("999.99"));

        Product product = new Product(productId, "Smartphone", price, category, 10);


        assertThat(product.getId()).isEqualTo(productId);
        assertThat(product.getName()).isEqualTo("Smartphone");
        assertThat(product.getPrice().getAmount()).isEqualTo(new BigDecimal("999.99"));
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getQuantity()).isEqualTo(10);

    }


    @Test
    void shouldThrowExceptionForEmptyProductName() {

        Category category = new Category(UUID.randomUUID(), "Electronics");
        Money price = new Money(new BigDecimal("999.99"));

        assertThatThrownBy(() -> new Product(UUID.randomUUID(), "", price, category, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product name cannot be empty");
    }
}