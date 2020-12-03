package com.example.simplecache.infrastructure;

import com.example.simplecache.domain.Category;

import java.util.List;

public interface CustomCategoryRepository {

    List<Category> find();

}
