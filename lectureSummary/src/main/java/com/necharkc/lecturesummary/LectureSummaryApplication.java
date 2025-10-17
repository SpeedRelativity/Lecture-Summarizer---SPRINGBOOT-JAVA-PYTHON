package com.necharkc.lecturesummary;

// My imports
import java.util.UUID;


// Spring imports
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LectureSummaryApplication {

    enum JobStatus { QUEUED, PROCESSING, COMPLETE, FAILED };

    public static void main(String[] args) {
        SpringApplication.run(LectureSummaryApplication.class, args);
    }

    @GetMapping()
    public String startPythonWorker(){
        return "Lecture Summary Application started";
    }

    @PostMapping("/submission")
    public JobResponse submittedAPI(@RequestBody SubmittedRequest request){
        String id =  UUID.randomUUID().toString();
        String url = request.url();

        return new JobResponse(id, url, JobStatus.QUEUED);
    }
}
