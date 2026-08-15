package com.devhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devhire.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    
}
