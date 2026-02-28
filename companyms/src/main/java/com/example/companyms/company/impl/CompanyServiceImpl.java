package com.example.companyms.company.impl;

import com.example.companyms.company.Company;
import com.example.companyms.company.CompanyRepository;
import com.example.companyms.company.CompanyService;
import com.example.companyms.company.mapper.CompanyCreator;
import com.example.companyms.dto.CompanyWithReviewAndJobsDTO;
import com.example.companyms.external.Job;
import com.example.companyms.external.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<CompanyWithReviewAndJobsDTO> getAllCompanies() {

        List<Company> companies = companyRepository.findAll();
        List<Job> jobs = restTemplate.exchange("http://JOBMS:8081/jobs/partial",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Job>>() {
                }
        ).getBody();
        List<CompanyWithReviewAndJobsDTO> companyList = new ArrayList<>();
        for (Company company : companies) {
            List<Job> currentJobs = new ArrayList<>();
            List<Review> reviews = restTemplate.exchange("http://REVIEWMS:8083/review?companyId=" + company.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {
                    }
            ).getBody();
            if (jobs != null) {
                for (Job job : jobs) {
                    if (Objects.equals(job.getCompanyId(), company.getId())) {
                        currentJobs.add(job);
                    }
                }
            }
            companyList.add(CompanyCreator.createCompany(company, currentJobs, reviews));
        }
        return companyList;
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean remove(Long id) {
        if (getCompanyById(id) != null) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company updateCompanyById(Long id, Company company) {
        if (getCompanyById(id) != null) {
            Company companyToUpdate = new Company();
            companyToUpdate.setName(company.getName());
            companyToUpdate.setId(id);
            companyToUpdate.setDescription(company.getDescription());
            // companyToUpdate.setJobs(company.getJobs());
            // companyToUpdate.setReview(company.getReview());

            companyRepository.save(companyToUpdate);
            return companyToUpdate;
        }
        return null;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
