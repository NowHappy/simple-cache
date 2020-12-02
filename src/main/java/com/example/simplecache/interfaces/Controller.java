package com.example.simplecache.interfaces;

import com.example.simplecache.aggregation.CacheHandler;
import com.example.simplecache.common.CacheEviction;
import com.example.simplecache.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private final CacheHandler cacheHandler;

    public Controller(CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }

    @CacheEviction
    @GetMapping("/categories")
    List<String> getCategories() {
        return cacheHandler.getCategories();
    }

    @CacheEviction
    @GetMapping("categories/{categoryNo}/products")
    List<Product> getProductList(@PathVariable Integer categoryNo) {
        return cacheHandler.getProductList(categoryNo);
    }

    @CacheEviction
    @GetMapping("/products/{productNo}")
    Product getProduct(@PathVariable Long productNo) {
        return cacheHandler.getProduct(productNo);
    }

    // 카테고리 관련해서 캐시 제거(갱신)
    // 카테고리 추가
//    @PostMapping("/categories")
//    Category createCategory(@RequestBody ) {
//
//    }

    // 카테고리 수정
    // 카테고리 삭제

    // 상품 추가, 수정, 삭제 / 목록 조회는 해당 상품의 카테고리가 캐시에 있으면 갱신 / 상품 캐시 있으면 갱신

}
