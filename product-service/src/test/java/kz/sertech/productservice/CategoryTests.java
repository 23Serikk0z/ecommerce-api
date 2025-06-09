package kz.sertech.productservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import kz.sertech.productservice.domain.model.Category;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategoryTests {



    @Test
    void shouldCreateCategoryWithValidName() {
        UUID uuid = UUID.randomUUID();

        Category category = new Category(uuid, "Electronics");


        assertThat(category.getId()).isEqualTo(uuid);
        assertThat(category.getName()).isEqualTo("Electronics");
    }


    @Test
    void shouldThrowExceptionForEmptyCategoryName() {
        UUID uuid = UUID.randomUUID();
        assertThatThrownBy(() -> new Category(uuid, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Category name cannot be empty");
    }
}
