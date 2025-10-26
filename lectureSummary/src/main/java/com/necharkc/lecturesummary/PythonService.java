package com.necharkc.lecturesummary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class PythonService {

    private final ObjectMapper mapper = new ObjectMapper();

    public ProcessResult PythonVideo(String jobId, String youtubeUrl) {

        try {
            ProcessBuilder builder = new ProcessBuilder("python", "worker.py", jobId, youtubeUrl);

            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                JsonNode node = mapper.readTree(output.toString());
                return new ProcessResult(true, node.get("title").asText(), node.get("transcription").asText(), node.get("summary").asText(), null);
            } else {
                return new ProcessResult(false, null, null, null, "process failed");
            }

        } catch (Exception e) {
            return new ProcessResult(false, null, null, null, "process failed due to " + e.getMessage());
        }
    }

    public static class ProcessResult{
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
