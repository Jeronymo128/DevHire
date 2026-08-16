package com.devhire.model;

import com.devhire.enums.JobType;
import com.devhire.enums.JobStatus;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "jobs")
public class Job {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Location is required")
    private String location;
    @Enumerated(EnumType.STRING)
    private JobType type;
    @Enumerated(EnumType.STRING)
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
public String getDescription(){
    return description;
}
public void setDescription(String description){
    this.description = description;
}
public String getLocation(){
    return location;
}
public void setLocation(String location){
    this.location = location;
}
public JobType getType(){
    return type;
}
public void setType(JobType type){
    this.type = type;
}
public JobStatus getStatus(){
    return status;
}
public void setStatus(JobStatus status){
    this.status = status;
}

}
