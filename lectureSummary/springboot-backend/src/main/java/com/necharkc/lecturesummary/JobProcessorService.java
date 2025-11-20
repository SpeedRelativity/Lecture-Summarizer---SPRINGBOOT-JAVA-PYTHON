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
    private final PythonApiService pythonApiService;
    public JobProcessorService(JobRepository jobRepository, PythonApiService pythonApiService) {
        this.jobRepository = jobRepository;
        this.pythonApiService = pythonApiService;
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

            PythonApiService.ProcessResult result = pythonApiService.processVideo(job.getId(), job.getUrl());

            if (result.success) {
                // Update job with processing results
                job.setTitle(result.title);
                job.setTranscription(result.transcription);
                job.setSummary(result.summary);
                job.setStatus(JobStatus.COMPLETED);
                jobRepository.save(job);
                System.out.println("✅ Job completed: " + job.getId());
                System.out.println("   Title: " + result.title);
                System.out.println("   Transcription length: " + result.transcription.length());
                System.out.println("   Summary length: " + result.summary.length());
            }
            else {
                // Handle failure
                job.setStatus(JobStatus.FAILED);
                job.setErrorMessage(result.errorMessage);
                System.err.println("❌ Job failed: " + job.getId());
                System.err.println("   Error: " + result.errorMessage);
                jobRepository.save(job);
            }

            // wait 10 seconds
            Thread.sleep(10000);

        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
            job.setErrorMessage(e.getMessage());
            jobRepository.save(job);
            System.err.println("❌ Exception processing job: " + job.getId());
            System.err.println("   Error: " + e.getMessage());
        };


    }
}
