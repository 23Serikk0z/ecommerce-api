package kz.sertech.productservice.domain.service;

import kz.sertech.productservice.domain.model.Money;
import kz.sertech.productservice.domain.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class ProductDomainService {

    public void validateProduct(Product product) {

        if(product.getQuantity().equals(0)) {
            throw new IllegalArgumentException("The product has no quantity");
        }
    }

    public void applyDiscount(Product product, double discountPercentage) {
        if(discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("The discount percentage must be between 0 and 100");
        }

        Money originalPrice = product.getPrice();
        BigDecimal discount = originalPrice.getAmount()
                .multiply(BigDecimal.valueOf(discountPercentage / 100));

        Money discountedPrice = new Money(originalPrice.getAmount().subtract(discount).setScale(2, RoundingMode.HALF_UP));
        product.update(product.getName(), discountedPrice, product.getCategory(), product.getQuantity());
    }

    public boolean isProductAvailable(Product product, int requestedQuantity) {
        return product.getQuantity() >= requestedQuantity;
    }
}
