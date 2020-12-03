package com.example.simplecache.interfaces;

import com.example.simplecache.aggregation.CacheHandler;
import com.example.simplecache.common.CacheEviction;
import com.example.simplecache.domain.Category;
import com.example.simplecache.domain.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    private final CacheHandler cacheHandler;

    public Controller(CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }

    // 카테고리 리스트 조회
    @CacheEviction
    @GetMapping("/categories")
    List<String> getCategories() {
        return cacheHandler.getCategories();
    }

    // 카테고리 상품 리스트 조회
    @CacheEviction
    @GetMapping("/categories/{categoryNo}/products")
    List<Product> getProductList(@PathVariable Integer categoryNo) {
        return cacheHandler.getProductList(categoryNo);
    }

    // 상품 정보 조회
    @CacheEviction
    @GetMapping("/products/{productNo}")
    Product getProduct(@PathVariable Long productNo) {
        return cacheHandler.getProduct(productNo);
    }

    // 카테고리 관련해서 캐시 제거(갱신)
    // 카테고리 추가
    @PostMapping("/categories")
    Category createCategory(@RequestBody Category category) {
        return cacheHandler.saveCategory(category);
    }

    // 카테고리 수정
    @PutMapping("/categories/{categoryNo}")
    Category updateCategory(@PathVariable Integer categoryNo,
                            @RequestBody Category category) {
        return cacheHandler.updateCategory(categoryNo, category);
    }

    // 카테고리 삭제
    @DeleteMapping("/categories/{categoryNo}")
    void deleteCategory(@PathVariable Integer categoryNo) {
        cacheHandler.deleteCategory(categoryNo);
    }

    // 목록 조회는 해당 상품의 카테고리가 캐시에 있으면 갱신 / 상품 캐시 있으면 갱신
    // 상품 추가
    @PostMapping("/products")
    Product createProduct(@RequestBody Product product) {
        return cacheHandler.saveProduct(product);
    }

    // 상품 수정
    @PutMapping("/products/{productNo}")
    Product updateProduct(@PathVariable Long productNo,
                          @RequestBody Product product) {
        return cacheHandler.updateProduct(productNo, product);
    }

    // 상품 삭제
    @DeleteMapping("/products/{productNo}")
    void deleteProduct(@PathVariable Long productNo) {
        cacheHandler.deleteProduct(productNo);
    }

}
