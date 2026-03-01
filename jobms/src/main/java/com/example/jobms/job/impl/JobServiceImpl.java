package com.example.jobms.job.impl;

import com.example.jobms.client.CompanyClient;
import com.example.jobms.client.ReviewClient;
import com.example.jobms.dto.JobWithCompanyDTO;
import com.example.jobms.external.Company;
import com.example.jobms.external.Review;
import com.example.jobms.job.Job;
import com.example.jobms.job.JobRepository;
import com.example.jobms.job.JobService;
import com.example.jobms.job.mapper.JobWithCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    JobRepository jobRepository;
    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    @Autowired
    RestTemplate restTemplate;
    @Override
    public List<JobWithCompanyDTO> findAll() {

        List<Job>jobs=jobRepository.findAll();
        List<JobWithCompanyDTO>jobWithCompanyDTOs=new ArrayList<>();

//        RestTemplate restTemplate=new RestTemplate();

        for (Job job:jobs){
            Company company=companyClient.getCompany(job.getCompanyId());
            List<Review>reviews=reviewClient.getReviews(job.getCompanyId());
            JobWithCompanyDTO jobWithCompanyDTO=JobWithCompanyMapper.mapJobWithCompany(job,company,reviews);
            jobWithCompanyDTOs.add(jobWithCompanyDTO);
        }
        return jobWithCompanyDTOs;
    }

    @Override
    public List<Job> find() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public boolean remove(Long id) {
        if(findById(id)==null)
            return false;
        jobRepository.deleteById(id);
        return true;
    }

    @Override
    public JobWithCompanyDTO findById(Long id) {
        Job job=jobRepository.findById(id).orElse(null);
        if (job!=null) {
            Company company =restTemplate.getForObject("http://COMPANYMS:8082/company/"+job.getCompanyId(),
                    Company.class);
            List<Review>reviews=restTemplate.exchange("http://REVIEWMS:8083/review?companyId" + job.getCompanyId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {
                    }).getBody();

            return JobWithCompanyMapper.mapJobWithCompany(job,company,reviews);
        }

        return null;
    }

    @Override
    public Job updateById(Long id, Job updatedJob) {
        Job currentJob=jobRepository.findById(id).orElse(null);
        if(currentJob!=null){
            currentJob.setDescription(updatedJob.getDescription());
            currentJob.setLocation(updatedJob.getLocation());
            currentJob.setTitle(updatedJob.getTitle());
            currentJob.setMinSalary(updatedJob.getMinSalary());
            currentJob.setMaxSalary(updatedJob.getMaxSalary());
            jobRepository.save(currentJob);
            return currentJob;
        }
        return null;
    }
}
