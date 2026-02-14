package com.example.jobApplication.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAll(Long companyId);
}
