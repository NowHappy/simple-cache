package com.example.simplecache.infrastructure;

import com.example.simplecache.domain.Category;
import com.example.simplecache.domain.Product;
import com.example.simplecache.domain.QCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CustomCategoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CategoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Category.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Product> findProductList() {
        return null;
    }
}
