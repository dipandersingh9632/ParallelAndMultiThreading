package com.learnjava.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Builder
public class Review {
    private int noOfReviews;
    private double overallRating;

    public Review(int noOfReviews, double overallRating) {
        this.noOfReviews = noOfReviews;
        this.overallRating = overallRating;
    }
}
