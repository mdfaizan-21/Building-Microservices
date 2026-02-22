package com.example.review.impl;


import com.example.review.Review;
import com.example.review.ReviewRepository;
import com.example.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
//    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAll(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean add(Long companyId, Review review) {

        if(companyId!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById( Long reviewId) {
//        List<Review>reviews=reviewRepository.findByCompanyId(companyId);

        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Review updateReviewById(Long reviewId, Review newReview) {
        Review review=getReviewById(reviewId);

        if(review!=null){
//            review.setCompany(newReview.getCompany());
            review.setDescription(newReview.getDescription());
            review.setRating(newReview.getRating());
            review.setTitle(newReview.getTitle());
            reviewRepository.save(review);
            return review;
        }
        return null;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
//        Company company=companyService.getCompanyById(companyId);
        Review review= reviewRepository.findById(reviewId).orElse(null);

        if(review !=null){
            reviewRepository.delete(review);
            return  true;
        }
        return false;
    }

}
