package com.necharkc.lecturesummary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LectureSummaryApplication {

    public static void main(String[] args) {

        SpringApplication.run(LectureSummaryApplication.class, args);


    }

    @GetMapping()
    public String startPythonWorker(){
        return "Lecture Summary Application started";
    }

    @GetMapping("/get")
    public String connectedAPI(){
        return "submission started";
    }

    @PostMapping("/submission")
    public String submittedAPI(){
        return "submission started";
    }
}
