package com.devhire.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devhire.dto.JobRequestDTO;
import com.devhire.dto.JobResponseDTO;
import com.devhire.model.Job;
import com.devhire.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;


    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;


    }
    public List<JobResponseDTO> getAllJobs() {
    return jobRepository.findAll()
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }
    
    public JobResponseDTO createJob(JobRequestDTO jobRequest) {
    Job job = new Job();

    job.setTitle(jobRequest.getTitle());
    job.setDescription(jobRequest.getDescription());
    job.setLocation(jobRequest.getLocation());
    job.setType(jobRequest.getType());
    job.setStatus(jobRequest.getStatus());

    Job savedJob = jobRepository.save(job);

    return toResponseDTO(savedJob);
    }
     

    public Optional<JobResponseDTO> getJobById(Long id) {
    return jobRepository.findById(id)
            .map(this::toResponseDTO);
    }
    
    public Optional<JobResponseDTO> updateJob(Long id, JobRequestDTO jobRequest) {
        return jobRepository.findById(id)
            .map(job -> {
                job.setTitle(jobRequest.getTitle());
                job.setDescription(jobRequest.getDescription());
                job.setLocation(jobRequest.getLocation());
                job.setType(jobRequest.getType());
                job.setStatus(jobRequest.getStatus());

                Job updatedJob = jobRepository.save(job);
                
                return toResponseDTO(updatedJob);
            });
    }
    public boolean deleteJob(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private JobResponseDTO toResponseDTO(Job job) {
    JobResponseDTO response = new JobResponseDTO();

    response.setId(job.getId());
    response.setTitle(job.getTitle());
    response.setDescription(job.getDescription());
    response.setLocation(job.getLocation());
    response.setType(job.getType());
    response.setStatus(job.getStatus());

    return response;
}

    
}
