package com.example.jobApplication.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAll(Long companyId);

    boolean add(Long companyId, Review review);

    Review getReviewById(Long companyId, Long reviewId);

    Review updateReviewById(Long companyId, Long reviewId, Review oldReview);

    boolean deleteReview(Long companyId, Long reviewId);
}
