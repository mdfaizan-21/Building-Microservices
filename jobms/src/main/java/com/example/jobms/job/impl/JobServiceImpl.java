package com.example.jobms.job.impl;

import com.example.jobms.job.Job;
import com.example.jobms.job.JobRepository;
import com.example.jobms.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    JobRepository jobRepository;
    @Override
    public List<Job> findAll() {
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
    public Job findById(Long id) {
        Optional<Job> job=jobRepository.findById(id);
        return job.orElse(null);
    }

    @Override
    public Job updateById(Long id, Job updatedJob) {
        Job currentJob=findById(id);
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
