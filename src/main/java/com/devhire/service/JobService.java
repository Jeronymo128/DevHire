package com.devhire.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devhire.model.Job;
import com.devhire.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;


    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;


    }
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    
}
