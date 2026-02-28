package com.example.jobms.job.mapper;

import com.example.jobms.dto.JobWithCompanyDTO;
import com.example.jobms.external.Company;
import com.example.jobms.job.Job;

public class JobWithCompanyMapper {
    public static JobWithCompanyDTO mapJobWithCompany(Job job, Company company){
        JobWithCompanyDTO jobWithCompanyDTO=new JobWithCompanyDTO();

        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());

        return jobWithCompanyDTO;
    }
}
