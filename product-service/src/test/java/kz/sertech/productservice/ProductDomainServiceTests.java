package kz.sertech.productservice;


import kz.sertech.productservice.domain.model.Category;
import kz.sertech.productservice.domain.model.Money;
import kz.sertech.productservice.domain.model.Product;
import kz.sertech.productservice.domain.service.ProductDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class ProductDomainServiceTests {


    private ProductDomainService productDomainService;
    private Product product;
    private Category category;
    private Money price;

    @BeforeEach
    void setUp() {
        productDomainService = new ProductDomainService();
        category = new Category(UUID.randomUUID(), "Electronics");
        price = new Money(new BigDecimal("1000.00"));
        product = new Product(UUID.randomUUID(), "Smartphone", price, category, 100);
    }


    @Test
    void shouldValidateProductWithSuccessData() {
        productDomainService.validateProduct(product);


        assertThat(product.getQuantity()).isEqualTo(100);
    }


    @Test
    void shouldThrowProductWithWrongData() {

        Product zeroQuantityProduct = new Product(UUID.randomUUID(), "Smartphone", price, category, 0);

        assertThatThrownBy(() -> productDomainService.validateProduct(zeroQuantityProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The product has no quantity");
    }

    @Test
    void shouldValidateApplyDiscountWithSuccessData() {
        productDomainService.applyDiscount(product, 10.0);

        assertThat(product.getQuantity()).isEqualTo(100);
        assertThat(product.getPrice().getAmount()).isEqualTo(new BigDecimal("900.00"));
        assertThat(product.getName()).isEqualTo("Smartphone");
        assertThat(product.getCategory()).isEqualTo(category);
    }

    @Test
    void shouldThrowForNegativeDiscount() {
        assertThatThrownBy(() -> productDomainService.applyDiscount(product, -10.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The discount percentage must be between 0 and 100");
    }

    @Test
    void shouldThrowExceptionForDiscountAbove100() {
        assertThatThrownBy(() -> productDomainService.applyDiscount(product, 150.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The discount percentage must be between 0 and 100");
    }

    @Test
    void shouldReturnTrueWhenSufficientQuantity() {
        boolean isAvailable = productDomainService.isProductAvailable(product, 50);

        assertThat(isAvailable).isTrue();
    }

    @Test
    void shouldReturnFalseWhenInsufficientQuantity() {
        boolean isAvailable = productDomainService.isProductAvailable(product, 150);

        assertThat(isAvailable).isFalse();
    }

    @Test
    void shouldReturnTrueWhenRequestedQuantityEqualsAvailable() {
        boolean isAvailable = productDomainService.isProductAvailable(product, 100);

        assertThat(isAvailable).isTrue();
    }
}
