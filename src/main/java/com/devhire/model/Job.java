package com.devhire.model;

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
    private String title;
    private String description;
    private String location;
    private String type;
    private String status;

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
public String getType(){
    return type;
}
public void setType(String type){
    this.type = type;
}
public String getStatus(){
    return status;
}
public void setStatus(String status){
    this.status = status;
}

}


