package com.example.simplecache.domain;

import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer categoryNo;

    String categoryName;

    Integer parentNo;

    @Column(nullable = false)
    Integer depth;

    public void addParentCategoryName(String name) {
        this.categoryName = name + "-" + this.categoryName;
    }

    public void updateCategory(Category category) {
        this.categoryName = category.getCategoryName();
        this.parentNo = category.getParentNo();
        this.depth = category.getDepth();
    }

}
