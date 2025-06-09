package kz.sertech.productservice.application.mapper;

import kz.sertech.productservice.application.dto.ProductCreateRequest;
import kz.sertech.productservice.application.dto.ProductResponse;
import kz.sertech.productservice.domain.model.Category;
import kz.sertech.productservice.domain.model.Money;
import kz.sertech.productservice.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "price", source = "price", qualifiedByName = "toMoney")
    Product toEntity(ProductCreateRequest request, Category category);

    @Mapping(target = "price", source = "price.amount")
    @Mapping(target = "categoryId", source = "category.id")
    ProductResponse toResponse(Product product);

    @Named("toMoney")
    default Money toMoney(BigDecimal price) {
        return new Money(price);
    }
}
