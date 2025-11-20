package com.necharkc.lecturesummary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;


@Service
public class PythonApiService {

    private final RestTemplate restTemplate;

    @Value("${python.service.url:http://localhost:8001}")
    private String pythonServiceUrl;

    public PythonApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProcessResult processVideo(String jobId, String youtubeUrl) {
        try {

            System.out.println("Calling Python service for jobId: " + jobId + " with URL: " + youtubeUrl);
            
            VideoRequest request = new VideoRequest(jobId, youtubeUrl);
            String endpoint = pythonServiceUrl + "/process";

            VideoResponse response = restTemplate.postForObject(endpoint, request, VideoResponse.class);

            if (response == null) {
                return new ProcessResult(false, null, null, null, "No response from Python service");
            }
            if (!response.success) {
               return new ProcessResult(false, null, null, null, response.error);
            }

            return new ProcessResult(true, response.title, response.transcript, response.summary, null);
        } catch (RestClientException e) {
            return new ProcessResult(false, null, null, null, "Error calling Python service: " + e.getMessage());
        }
    }

    public static class VideoRequest {
        public String job_id;
        public String video_url;

        public VideoRequest(String job_id, String video_url) {
            this.job_id = job_id;
            this.video_url = video_url;
        }
    }

    public static class VideoResponse {
        public boolean success;
        public String title;
        public String transcript;
        public String summary;
        public String keyPoints;
        public String error;
    }

     public static class ProcessResult {
        public final boolean success;
        public final String title;
        public final String transcription;
        public final String summary;
        public final String errorMessage;

        public ProcessResult(boolean success, String title, String transcription, String summary, String errorMessage) {
            this.success = success;
            this.title = title;
            this.transcription = transcription;
            this.summary = summary;
            this.errorMessage = errorMessage;
        }
    }


}

