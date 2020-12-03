package com.example.simplecache.infrastructure;

import com.example.simplecache.domain.Category;
import com.example.simplecache.domain.Product;
import com.example.simplecache.domain.cache.Cache;
import com.example.simplecache.domain.cache.CacheService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CacheServiceImpl implements CacheService {

    private final Map<String, Long> cacheLoadTime = new ConcurrentHashMap<>();
    private final long cacheDuration = 60 * 15 * 1000L;

    private final static String CATEGORY_KEY = "category";
    private final static String PRODUCT_LIST_KEY_PREFIX = "product-list-";
    private final static String PRODUCT_KEY_PREFIX = "product-";

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CacheServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<String> findCategories() {
        long now = System.currentTimeMillis();
        long categoriesLoadTime = now;
        if (cacheLoadTime.containsKey(CATEGORY_KEY)) {
            categoriesLoadTime = cacheLoadTime.get(CATEGORY_KEY);
        }

        if (!cache.containsKey(CATEGORY_KEY) || now - categoriesLoadTime > cacheDuration) {
            cache.remove(CATEGORY_KEY);
            List<String> list = categoryRepository.findAll().stream().map(category -> {
                if (category.getDepth().equals(2)) {
                    category.addParentCategoryName(categoryRepository.findByCategoryNo(category.getParentNo()).get().getCategoryName());
                }
                return category.getCategoryName();
            }).collect(Collectors.toList());
            cache.put(CATEGORY_KEY, new Cache(list));
            cacheLoadTime.put(CATEGORY_KEY, now);

        } else {
            cache.get(CATEGORY_KEY).setHowManyUsed(cache.get(CATEGORY_KEY).getHowManyUsed() + 1);
        }

        return (List<String>)cache.get(CATEGORY_KEY).getValue();
    }

    @Override
    public void invalidateCategories() {
        cache.remove(CATEGORY_KEY);
    }

    @Override
    public List<Product> findProductList(Integer categoryNo) {
        long now = System.currentTimeMillis();

        String cacheKey = PRODUCT_LIST_KEY_PREFIX + categoryNo;

        long productListLoadTime = now;
        if (cacheLoadTime.containsKey(cacheKey)) {
            productListLoadTime = cacheLoadTime.get(cacheKey);
        }

        if (!cache.containsKey(cacheKey) || now - productListLoadTime > cacheDuration) {
            cache.remove(cacheKey);
            cache.put(cacheKey, new Cache(productRepository.findByCategoryNo(categoryNo)));
            cacheLoadTime.put(cacheKey, now);

        } else {
            cache.get(cacheKey).setHowManyUsed(cache.get(cacheKey).getHowManyUsed() + 1);
        }

        return (List<Product>)cache.get(cacheKey).getValue();
    }

    @Override
    public void invalidateProductList(String cacheKey) {
        if (cache.containsKey(cacheKey)) {
            cache.remove(cacheKey);
        }
    }

    @Override
    public Product findProduct(Long productNo) {
        long now = System.currentTimeMillis();

        String cacheKey = PRODUCT_KEY_PREFIX + productNo;

        long productLoadTime = now;
        if (cacheLoadTime.containsKey(cacheKey)) {
            productLoadTime = cacheLoadTime.get(cacheKey);
        }

        if (!cache.containsKey(cacheKey) || now - productLoadTime > cacheDuration) {
            cache.remove(cacheKey);
            cache.put(cacheKey, new Cache(productRepository.findById(productNo).get()));
            cacheLoadTime.put(cacheKey, now);

        } else {
            cache.get(cacheKey).setHowManyUsed(cache.get(cacheKey).getHowManyUsed() + 1);
        }

        return (Product)cache.get(cacheKey).getValue();
    }

    @Override
    public void invalidateProduct(String cacheKey) {
        if (cache.containsKey(cacheKey)) {
            cache.remove(cacheKey);
        }
    }

    @Override
    public Category saveCategory(Category category) {
        if (cache.containsKey(CATEGORY_KEY)) {
            cache.remove(CATEGORY_KEY);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(Integer categoryNo) {
        return categoryRepository.findById(categoryNo).orElseThrow();
    }

    @Override
    public void deleteCategory(Integer categoryNo) {
        if (cache.containsKey(CATEGORY_KEY)) {
            invalidateCategories();
        }
        categoryRepository.deleteById(categoryNo);
    }

    @Override
    public Product saveProduct(Product product) {
        invalidateProductList(PRODUCT_LIST_KEY_PREFIX + product.getCategoryNo());
        invalidateProduct(PRODUCT_KEY_PREFIX + product.getProductNo());
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productNo) {
        return productRepository.findById(productNo).orElseThrow();
    }

    @Override
    public void deleteProduct(Long productNo) {
        Product product = findProductById(productNo);
        invalidateProductList(PRODUCT_LIST_KEY_PREFIX + product.getCategoryNo());
        invalidateProduct(PRODUCT_KEY_PREFIX + productNo);
        productRepository.deleteById(productNo);
    }

}
