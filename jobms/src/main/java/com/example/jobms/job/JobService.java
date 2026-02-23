package com.example.jobms.job;

import com.example.jobms.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO>findAll();
    void createJob(Job job);
    boolean remove(Long id);
    Job findById(Long id);

    Job updateById(Long id, Job updatedJob);
}
