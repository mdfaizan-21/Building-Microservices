package com.example.jobms.job;

import java.util.List;

public interface JobService {
    List<Job>findAll();
    void createJob(Job job);
    boolean remove(Long id);
    Job findById(Long id);

    Job updateById(Long id, Job updatedJob);
}
