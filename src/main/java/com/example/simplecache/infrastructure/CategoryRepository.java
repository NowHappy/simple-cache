package com.example.simplecache.infrastructure;

import com.example.simplecache.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CustomCategoryRepository {

    Optional<Category> findByCategoryNo(Integer no);

}
