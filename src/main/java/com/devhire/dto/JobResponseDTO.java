package com.devhire.dto;

import com.devhire.enums.JobStatus;
import com.devhire.enums.JobType;

public class JobResponseDTO {
  private Long id;
  private String title;
  private String description;
  private String location;
  private JobType type;
  private JobStatus status;
  
  
public Long getId() {
    return id;
  }
public void setId(Long id) {
    this.id = id;
  }
public String getTitle() {
    return title;
  }
public void setTitle(String title) {
    this.title = title;
  }
public String getDescription() {
    return description;
  }
public void setDescription(String description) {
    this.description = description;
  }
public String getLocation() {
    return location;
  }
public void setLocation(String location) {
    this.location = location;
  }
public JobType getType() {
    return type;
  }
public void setType(JobType type) {
    this.type = type;
  }
public JobStatus getStatus() {
    return status;
  }
public void setStatus(JobStatus status) {
    this.status = status;
  }
  
}
