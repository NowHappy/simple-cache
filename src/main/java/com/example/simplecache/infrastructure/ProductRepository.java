package com.example.simplecache.infrastructure;

import com.example.simplecache.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryNo(Integer categoryNo);

}
