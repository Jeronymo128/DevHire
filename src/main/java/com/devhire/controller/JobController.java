package com.devhire.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devhire.model.Job;
import com.devhire.service.JobService;
import com.devhire.dto.JobRequestDTO;
import com.devhire.dto.JobResponseDTO;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @GetMapping
    public List<JobResponseDTO> getAllJobs(){
        return jobService.getAllJobs();


    }
    @PostMapping
    public JobResponseDTO createJob(@Valid @RequestBody JobRequestDTO jobRequest) {
        return jobService.createJob(jobRequest);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJobById(@PathVariable Long id) {
        return jobService.getJobById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(
        @PathVariable Long id,
        @Valid @RequestBody JobRequestDTO jobRequest) {

        return jobService.updateJob(id, jobRequest)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
    @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
       if (jobService.deleteJob(id)) {
        return ResponseEntity.noContent().build();
      }

        return ResponseEntity.notFound().build();
    }
}

