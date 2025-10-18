package com.necharkc.lecturesummary;

// My imports
import java.time.LocalDateTime;
import java.util.UUID;


// Spring imports
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;



@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173")
@RestController

public class LectureSummaryApplication {

    private final JobRepository jobRepository;

    public LectureSummaryApplication(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LectureSummaryApplication.class, args);
    }

    @GetMapping()
    public String startPythonWorker(){
        return "Lecture Summary Application started";
    }

    @PostMapping("/submission")
    public JobResponse submittedAPI(@RequestBody SubmittedRequest request){

        Job job = new Job();
        job.setId(UUID.randomUUID().toString());
        job.setUrl(request.url());
        job.setStatus(JobStatus.QUEUED);
        job.setCreatedAt(LocalDateTime.now());

        jobRepository.save(job);

        return new JobResponse(job.getId(), job.getUrl(), job.getStatus());
    }
}
