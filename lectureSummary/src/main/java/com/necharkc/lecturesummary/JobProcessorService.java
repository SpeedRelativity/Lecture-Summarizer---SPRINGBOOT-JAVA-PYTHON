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
    private final PythonService pythonService;

    public JobProcessorService(JobRepository jobRepository, PythonService pythonService) {
        this.jobRepository = jobRepository;
        this.pythonService = pythonService;
        System.out.println("✅ JobProcessorService created!");
        System.out.println("✅ PythonService created!");
    }

    @Scheduled(fixedDelay = 10000)
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

            PythonService.ProcessResult result = pythonService.processVideo(job.getId(), job.getUrl());

            if (result.success) {
                job.setStatus(JobStatus.COMPLETED);
                jobRepository.save(job);
                System.out.println("✅ Job completed: " + job.getId());
            }
            else {
                // Handle failure
                job.setStatus(JobStatus.FAILED);
                job.setErrorMessage(result.errorMessage);
                System.err.println("❌ Job failed: " + job.getId());
            }
            jobRepository.save(job);
            // wait 10 seconds
            Thread.sleep(10000);

        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
            job.setErrorMessage(e.getMessage());
            jobRepository.save(job);
        };


    }
}
