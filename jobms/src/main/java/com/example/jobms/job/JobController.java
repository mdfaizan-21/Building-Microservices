package com.example.jobms.job;

import com.example.jobms.dto.JobWithCompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobWithCompanyDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<Job> addNewJob(@RequestBody Job currentJob) {
        jobService.createJob(currentJob);
        return ResponseEntity.status(201).body(currentJob);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDTO>getJob(@PathVariable Long id){
        JobWithCompanyDTO job=jobService.findById(id);
        return job!=null?ResponseEntity.ok(job):ResponseEntity.status(404).body(job);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean removed = jobService.remove(id);

        if (!removed) {
            return ResponseEntity.status(404).body("Job not found");
        }

        return ResponseEntity.ok("Job deleted successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Job>updateJob(@PathVariable Long id,@RequestBody Job UpdatedJob){
        Job job=jobService.updateById(id,UpdatedJob);
        return job!=null?ResponseEntity.ok(job):ResponseEntity.status(404).body(null);
    }

}
