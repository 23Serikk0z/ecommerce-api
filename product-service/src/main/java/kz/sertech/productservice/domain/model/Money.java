package kz.sertech.productservice.domain.model;


import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;

@Embeddable
@Getter
public class Money {

    private BigDecimal amount;

    protected Money() {}

    public Money(BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }
}
