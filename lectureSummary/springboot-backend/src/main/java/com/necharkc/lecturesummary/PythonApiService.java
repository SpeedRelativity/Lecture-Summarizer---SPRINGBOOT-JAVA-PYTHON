package com.necharkc.lecturesummary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;


@Service
public class PythonApiService {

    private final RestTemplate restTemplate;
    private final String pythonApiUrl;

    @Value("${python.service.url:http://localhost:8001}")
    private String pythonServiceUrl;

    public PythonApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String requestLectureSummary(String videoUrl) {
        String requestUrl = String.format("%s/summarize?videoUrl=%s", pythonApiUrl, videoUrl);
        try {
            return restTemplate.getForObject(requestUrl, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch lecture summary from Python API", e);
        }
    }
}

