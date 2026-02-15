package com.example.jobApplication.review.impl;

import com.example.jobApplication.company.Company;
import com.example.jobApplication.company.CompanyService;
import com.example.jobApplication.review.Review;
import com.example.jobApplication.review.ReviewRepository;
import com.example.jobApplication.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAll(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean add(Long companyId, Review review) {
        Company company=companyService.getCompanyById(companyId);

        if(company!=null){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById(Long companyId, Long reviewId) {
        List<Review>reviews=reviewRepository.findByCompanyId(companyId);

        return reviews.stream()
                .filter(review1 -> review1.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Review updateReviewById(Long companyId, Long reviewId, Review newReview) {
        Review review=getReviewById(companyId,reviewId);

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
    public boolean deleteReview(Long companyId, Long reviewId) {
        Company company=companyService.getCompanyById(companyId);
        Review review= reviewRepository.findById(reviewId).orElse(null);

        if(company!=null && review !=null){
            getAll(companyId).remove(review);
            reviewRepository.deleteById(reviewId);
            return  true;
        }
        return false;
    }

}
