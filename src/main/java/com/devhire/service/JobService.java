package com.devhire.service;

import java.util.List;
import java.util.Optional;

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
    public Job createJob(Job job) {
        return jobRepository.save(job);
     
    }
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }
    public Optional<Job> updateJob(Long id, Job jobDetails) {
        return jobRepository.findById(id)
            .map(job -> {
                job.setTitle(jobDetails.getTitle());
                job.setDescription(jobDetails.getDescription());
                job.setLocation(jobDetails.getLocation());
                job.setType(jobDetails.getType());
                job.setStatus(jobDetails.getStatus());

                return jobRepository.save(job);
            });
    }
    public boolean deleteJob(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    
}
