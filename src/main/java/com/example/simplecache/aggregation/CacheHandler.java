package com.example.simplecache.aggregation;

import com.example.simplecache.domain.Category;
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

    public Category saveCategory(Category category) {
        return cacheService.saveCategory(category);
    }

    public Category updateCategory(Integer categoryNo, Category category) {
        Category fetchedCategory = cacheService.findCategoryById(categoryNo);
        fetchedCategory.updateCategory(category);
        return cacheService.saveCategory(fetchedCategory);
    }

    public void deleteCategory(Integer categoryNo) {
        cacheService.deleteCategory(categoryNo);
    }

    public Product saveProduct(Product product) {
        return cacheService.saveProduct(product);
    }

    public Product updateProduct(Long productNo, Product product) {
        Product fetchedProduct = cacheService.findProductById(productNo);
        fetchedProduct.updateProduct(product);
        return cacheService.saveProduct(fetchedProduct);
    }

    public void deleteProduct(Long productNo) {
        cacheService.deleteProduct(productNo);
    }

}
