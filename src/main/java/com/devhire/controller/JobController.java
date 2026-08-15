package com.devhire.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devhire.model.Job;
import com.devhire.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @GetMapping
    public List<Job> getAllJobs(){
        return jobService.getAllJobs();


    }

}