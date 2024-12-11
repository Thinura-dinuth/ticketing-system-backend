package com.example.Ticketing;

import JavaCLI.Main;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class ConfigController {

    private static final String FILE_PATH = "config.txt"; // Use relative path
    private static final String CLASS_PATH = "target/classes"; // Use relative path
    private Process process;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostMapping("/save-config")
    public String saveConfig(@RequestBody String config) {
        try (FileWriter fileWriter = new FileWriter(Paths.get(FILE_PATH).toFile(), false)) {
            fileWriter.write(config);
        } catch (IOException e) {
            return "Error saving configuration: " + e.getMessage();
        }
        return "Configuration saved successfully";
    }

    @PostMapping("/start-process")
    public String startProcess() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", CLASS_PATH, "JavaCLI.Main", "start");
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();

            executorService.submit(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Send the output to the frontend (e.g., using WebSocket or other means)
                        // For simplicity, we will just print it here
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            return "Error starting process: " + e.getMessage();
        }
        return "Process started successfully";
    }

    @PostMapping("/stop-process")
    public String stopProcess() {
        if (process != null) {
            process.destroy();
            return "Process stopped successfully";
        }
        return "No process to stop";
    }
}