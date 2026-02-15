package com.example.jobApplication.review;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/review")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId){
        return ResponseEntity.ok(reviewService.getAll(companyId));
    }

    @PostMapping("/review")
    public ResponseEntity<String > addReview(@PathVariable Long companyId, @RequestBody Review review){
        boolean isReviewAdded=reviewService.add(companyId,review);

        if (isReviewAdded){
            return new ResponseEntity<>("Review Added Successfully", HttpStatusCode.valueOf(200));
        }

        return new ResponseEntity<>("Review cannot be added", HttpStatusCode.valueOf(400));

    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId,@PathVariable Long reviewId){
        Review review=reviewService.getReviewById(companyId,reviewId);
        if (review!=null)
            return ResponseEntity.ok(review);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @RequestBody Review oldreview
    ){

        Review review=reviewService.updateReviewById(companyId,reviewId,oldreview);
        if (review!=null)
            return ResponseEntity.ok(review);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId
    ){

        boolean isDeleted =reviewService.deleteReview(companyId,reviewId);
        if (isDeleted)
            return ResponseEntity.ok("Review deleted successfully");
        return ResponseEntity.status(404).body("Review cannot be deleted");
    }




}
