package com.necharkc.lecturesummary;

// My imports
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


// Spring imports
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;



@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@EnableScheduling

public class LectureSummaryApplication {

    private final JobRepository jobRepository;

    public LectureSummaryApplication(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LectureSummaryApplication.class, args);
    }

    @PostMapping("/submission")
    public JobResponse submittedAPI(@RequestBody SubmittedRequest request){

        Job job = new Job();
        job.setId(UUID.randomUUID().toString());
        job.setUrl(request.ytlink());
        job.setStatus(JobStatus.QUEUED);
        job.setCreatedAt(LocalDateTime.now());

        jobRepository.save(job);

        return new JobResponse(job.getId(), job.getUrl(), job.getStatus());
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/jobs/{jobId}")
    public JobResponse getJobStatus(@PathVariable String jobId){ // jobId is dynamic, so its of type path variable.
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found." + jobId));
        System.out.println("id requested");
        return new JobResponse(job.getId(), job.getUrl(), job.getStatus());
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/jobs")
    public List<JobResponse> allJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(job -> new JobResponse(job.getId(), job.getUrl(), job.getStatus())).toList();
    }

}
