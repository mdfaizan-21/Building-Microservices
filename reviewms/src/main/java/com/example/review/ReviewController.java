package com.example.review;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return ResponseEntity.ok(reviewService.getAll(companyId));
    }

    @PostMapping
    public ResponseEntity<String > addReview(@RequestParam Long companyId, @RequestBody Review review){
        boolean isReviewAdded=reviewService.add(companyId,review);

        if (isReviewAdded){
            return new ResponseEntity<>("Review Added Successfully", HttpStatusCode.valueOf(200));
        }

        return new ResponseEntity<>("Review cannot be added", HttpStatusCode.valueOf(400));

    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
        Review review=reviewService.getReviewById(reviewId);
        if (review!=null)
            return ResponseEntity.ok(review);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review oldreview
    ){

        Review review=reviewService.updateReviewById(reviewId,oldreview);
        if (review!=null)
            return ResponseEntity.ok(review);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId
    ){
        boolean isDeleted =reviewService.deleteReview(reviewId);
        if (isDeleted)
            return ResponseEntity.ok("Review deleted successfully");
        return ResponseEntity.status(404).body("Review cannot be deleted");
    }




}
