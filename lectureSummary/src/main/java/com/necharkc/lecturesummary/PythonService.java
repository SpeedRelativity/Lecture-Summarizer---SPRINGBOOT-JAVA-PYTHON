package com.necharkc.lecturesummary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class PythonService {

    private final ObjectMapper mapper = new ObjectMapper();

    public ProcessResult processVideo(String jobId, String youtubeUrl) {
        try {
            System.out.println("🐍 Starting Python process for job: " + jobId);
            System.out.println("URL: " + youtubeUrl);

            // Build the command
            ProcessBuilder builder = new ProcessBuilder(
                    "python",      // Change to "python3" on Mac/Linux if needed
                    "worker.py",
                    jobId,
                    youtubeUrl
            );

            // Merge error stream with output stream
            builder.redirectErrorStream(true);

            // Start the process
            Process process = builder.start();

            // Read output
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("Python: " + line);  // Log each line
                output.append(line);
            }

            // Wait for process to complete
            int exitCode = process.waitFor();
            System.out.println("🐍 Python process finished with exit code: " + exitCode);

            if (exitCode == 0) {
                // Parse JSON response
                String jsonOutput = output.toString();
                System.out.println("JSON output length: " + jsonOutput.length());

                JsonNode node = mapper.readTree(jsonOutput);

                // Extract fields
                String title = node.get("title").asText();
                String transcription = node.get("transcription").asText();
                String summary = node.get("summary").asText();

                System.out.println("✅ Job completed successfully: " + jobId);
                System.out.println("Title: " + title);
                System.out.println("Transcription length: " + transcription.length());
                System.out.println("Summary length: " + summary.length());

                return new ProcessResult(
                        true,
                        title,
                        transcription,
                        summary,
                        null
                );

            } else {
                System.err.println("❌ Python process failed with exit code: " + exitCode);
                return new ProcessResult(
                        false,
                        null,
                        null,
                        null,
                        "Python process failed with exit code: " + exitCode
                );
            }

        } catch (Exception e) {
            System.err.println("❌ Exception in PythonService: " + e.getMessage());
            e.printStackTrace();

            return new ProcessResult(
                    false,
                    null,
                    null,
                    null,
                    "Process failed: " + e.getMessage()
            );
        }
    }

    public static class ProcessResult {
        public final boolean success;
        public final String title;
        public final String transcription;
        public final String summary;
        public final String errorMessage;

        public ProcessResult(boolean success, String title, String transcription,
                             String summary, String errorMessage) {
            this.success = success;
            this.title = title;
            this.transcription = transcription;
            this.summary = summary;
            this.errorMessage = errorMessage;
        }
    }
}