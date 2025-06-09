package kz.sertech.productservice.domain.repository;

import kz.sertech.productservice.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}