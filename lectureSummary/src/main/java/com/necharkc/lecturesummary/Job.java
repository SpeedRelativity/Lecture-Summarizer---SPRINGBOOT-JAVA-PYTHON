package com.necharkc.lecturesummary;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    private String id;
    @Column(nullable = false) // this cannot be null
    private String url;
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    @Column(name = "title")
    private String title;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(columnDefinition = "TEXT")  // TEXT type for large strings
    private String transcription;
    @Column(columnDefinition = "TEXT")
    private String summary;
    private String errorMessage;


    public Job() {};

    public Job(String id, String url, JobStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.url = url;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public JobStatus getStatus() {
        return status;
    }
    public void setStatus(JobStatus status) {
        this.status = status;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getTranscription() {
        return transcription;
    }
    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString(){
        return "JobId " + id + " is of status "+ status;
    }

}
