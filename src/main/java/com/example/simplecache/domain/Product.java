package com.example.simplecache.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long productNo;

    String brandName;

    String productName;

    BigDecimal productPrice;

    Integer categoryNo;

    public void updateProduct(Product product) {
        this.brandName = product.getBrandName();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.categoryNo = product.getCategoryNo();
    }

}
