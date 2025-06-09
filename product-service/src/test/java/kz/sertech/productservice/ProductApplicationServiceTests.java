package kz.sertech.productservice;


import kz.sertech.productservice.application.dto.ProductCreateRequest;
import kz.sertech.productservice.application.dto.ProductResponse;
import kz.sertech.productservice.application.mapper.ProductMapper;
import kz.sertech.productservice.domain.model.Category;
import kz.sertech.productservice.domain.model.Money;
import kz.sertech.productservice.domain.model.Product;
import kz.sertech.productservice.domain.repository.CategoryRepository;
import kz.sertech.productservice.domain.repository.ProductRepository;
import kz.sertech.productservice.domain.service.ProductDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
public class ProductApplicationServiceTests {

    @Autowired
    private ProductApplicationService service;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private ProductDomainService productDomainService;

    private ProductCreateRequest productRequest;
    private Category category;
    private Product product;
    private ProductResponse response;


    @BeforeEach
    void setUp() {
        UUID categoryId = UUID.randomUUID();
        category = new Category(categoryId, "Electronics");
        productRequest = new ProductCreateRequest();
        productRequest.setCategoryId(categoryId);
        productRequest.setName("Smartphone");
        productRequest.setPrice(new BigDecimal("15.00"));
        productRequest.setQuantity(10);

        product = new Product(UUID.randomUUID(), "Smartphone", new Money(new BigDecimal("15.00")), category, 10);
        response = new ProductResponse();
        response.setId(product.getId());
        response.setName("Smartphone");
        response.setCategoryId(categoryId);
        response.setPrice(new BigDecimal("15.00"));
        response.setQuantity(10);
        response.setCategoryId(categoryId);

    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(categoryRepository.findById(productRequest.getCategoryId()))
                .thenReturn(Optional.of(category));

        when(productMapper.toEntity(productRequest, category)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(response);

        ProductResponse result = service.createProduct(request);

        verify(productDomainService).validateProduct(product);
        verify(productRepository).save(product);
        assertThat(result.getName()).isEqualTo("Smartphone");
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("15.00"));
        assertThat(result.getQuantity()).isEqualTo(10);
        assertThat(result.getCategoryId()).isEqualTo(category.getId());
    }
}
