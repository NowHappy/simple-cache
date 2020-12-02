package com.example.simplecache.infrastructure;

import com.example.simplecache.domain.Product;

import java.util.List;

public interface CustomCategoryRepository {

    List<Product> findProductList();

}
