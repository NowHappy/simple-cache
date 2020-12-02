package com.example.simplecache.domain.cache;

import com.example.simplecache.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface CacheService {

    List<String> findCategories();

    void invalidateCategoryGroups();

    List<Product> findProductList(Integer categoryNo);

    Product findProduct(Long productNo);

    Map<String, Cache> cache = new ConcurrentHashMap<>();

}
