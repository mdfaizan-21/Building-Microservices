package com.example.companyms.company.mapper;

import com.example.companyms.company.Company;
import com.example.companyms.dto.CompanyWithReviewAndJobsDTO;
import com.example.companyms.external.Job;
import com.example.companyms.external.Review;

import java.util.List;

public class CompanyCreator {
    public static CompanyWithReviewAndJobsDTO createCompany(Company company, List<Job> jobs, List<Review> reviews){
        CompanyWithReviewAndJobsDTO companyWithReviewAndJobsDTO=new CompanyWithReviewAndJobsDTO();

        companyWithReviewAndJobsDTO.setJobs(jobs);
        companyWithReviewAndJobsDTO.setDescription(company.getDescription());
        companyWithReviewAndJobsDTO.setReview(company.getReview());
        companyWithReviewAndJobsDTO.setName(company.getName());
        companyWithReviewAndJobsDTO.setId(company.getId());
        companyWithReviewAndJobsDTO.setReviews(reviews);
        return companyWithReviewAndJobsDTO;
    }
}
