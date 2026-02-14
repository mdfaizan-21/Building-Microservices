package com.example.jobApplication.review.impl;

import com.example.jobApplication.review.Review;
import com.example.jobApplication.review.ReviewRepository;
import com.example.jobApplication.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAll(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

}
