package com.example.jobms.job;

import com.example.jobms.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO>findAll();
    List<Job>find();
    void createJob(Job job);
    boolean remove(Long id);
    JobWithCompanyDTO findById(Long id);

    Job updateById(Long id, Job updatedJob);
}
