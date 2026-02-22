package com.example.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAll(Long companyId);

    boolean add(Long companyId, Review review);

    Review getReviewById(Long reviewId);

    Review updateReviewById(Long reviewId, Review oldReview);

    boolean deleteReview(Long reviewId);
}
