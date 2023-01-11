package com.learnjava.domain;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class Product {

    @NonNull
    private String productId;
    @NonNull
    private ProductInfo productInfo;
    @NonNull
    private Review review;

    public Product(String productId,ProductInfo productInfo,Review review) {
        this.productId = productId;
        this.productInfo = productInfo;
        this.review = review;
    }
}
