package com.example.Ticketing;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ConfigController {

    private static final String FILE_PATH = "D:/Second Year-Level 5/OOP/CW/Ticketing-System-Backend/config.txt";
    private static final String CLASS_PATH = "D:/Second Year-Level 5/OOP/CW/Ticketing-System-Backend/target/classes"; // Update this path

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
            List<String> configLines = Files.readAllLines(Paths.get(FILE_PATH));
            List<String> params = new ArrayList<>();
            for (String line : configLines) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    params.add(parts[1]);
                }
            }
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", CLASS_PATH, "JavaCLI.Main");
            processBuilder.command().addAll(params);
            processBuilder.inheritIO();
            processBuilder.start();
        } catch (IOException e) {
            return "Error starting process: " + e.getMessage();
        }
        return "Process started successfully";
    }
}