package com.example.jobms.job.impl;

import com.example.jobms.dto.JobWithCompanyDTO;
import com.example.jobms.external.Company;
import com.example.jobms.job.Job;
import com.example.jobms.job.JobRepository;
import com.example.jobms.job.JobService;
import com.example.jobms.job.mapper.JobWithCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;
    @Override
    public List<JobWithCompanyDTO> findAll() {

        List<Job>jobs=jobRepository.findAll();
        List<JobWithCompanyDTO>jobWithCompanyDTOs=new ArrayList<>();

//        RestTemplate restTemplate=new RestTemplate();

        for (Job job:jobs){
            Company company=restTemplate.getForObject("http://COMPANYMS:8082/company/"+job.getCompanyId(),
                    Company.class);
            JobWithCompanyDTO jobWithCompanyDTO=JobWithCompanyMapper.mapJobWithCompany(job,company);
            jobWithCompanyDTOs.add(jobWithCompanyDTO);
        }

        return jobWithCompanyDTOs;
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

            return JobWithCompanyMapper.mapJobWithCompany(job,company);
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
