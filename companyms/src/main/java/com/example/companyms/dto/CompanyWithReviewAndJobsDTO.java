package com.example.companyms.dto;

import com.example.companyms.company.Company;
import com.example.companyms.external.Job;
import com.example.companyms.external.Review;

import java.util.List;

public class CompanyWithReviewAndJobsDTO {
    private Long id;
    private String name;
    private String description;
    private String review;
    private List<Job>jobs;
    private List<Review>reviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
