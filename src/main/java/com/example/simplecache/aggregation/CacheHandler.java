package com.example.simplecache.aggregation;

import com.example.simplecache.domain.Product;
import com.example.simplecache.domain.cache.CacheService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheHandler {

    private final CacheService cacheService;

    public CacheHandler(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public List<String> getCategories() {
        return cacheService.findCategories();
    }

    public List<Product> getProductList(Integer categoryNo) {
        return cacheService.findProductList(categoryNo);
    }

    public Product getProduct(Long productNo) {
        return cacheService.findProduct(productNo);
    }

}
