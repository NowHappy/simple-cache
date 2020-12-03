package com.example.simplecache.domain.cache;

import com.example.simplecache.domain.Category;
import com.example.simplecache.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface CacheService {

    List<String> findCategories();

    void invalidateCategories();

    List<Product> findProductList(Integer categoryNo);

    void invalidateProductList(String cacheKey);

    Product findProduct(Long productNo);

    void invalidateProduct(String cacheKey);

    Map<String, Cache> cache = new ConcurrentHashMap<>();

    Category saveCategory(Category category);

    Category findCategoryById(Integer categoryNo);

    void deleteCategory(Integer categoryNo);

    Product saveProduct(Product product);

    Product findProductById(Long productNo);

    void deleteProduct(Long productNo);

}
