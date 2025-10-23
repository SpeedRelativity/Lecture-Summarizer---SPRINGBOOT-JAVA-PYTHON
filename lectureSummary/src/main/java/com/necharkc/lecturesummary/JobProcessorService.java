package com.necharkc.lecturesummary;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAsync
public class JobProcessorService {

    private final JobRepository jobRepository;

    public JobProcessorService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void processJobs() {

        System.out.println("🔍 Checking for queued jobs...");
        List<Job> queuedJobs = jobRepository.findByStatus(JobStatus.QUEUED);

        if (queuedJobs.isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        else {
            for (Job job : queuedJobs) {
                processJob(job);
            }
        }

    }
    @Async
    public void processJob(Job job) {
        try {
            job.setStatus(JobStatus.PROCESSING);
            jobRepository.save(job);

            // wait 10 seconds
            Thread.sleep(10000);

            job.setStatus(JobStatus.COMPLETED);
            jobRepository.save(job);

        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
            job.setErrorMessage(e.getMessage());
            jobRepository.save(job);
        };


    }
}
