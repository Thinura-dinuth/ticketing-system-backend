package Ticketing;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveConfiguration(Configuration config, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), config);
            System.out.println("Configuration saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration loadConfiguration(String fileName) {
        try {
            return objectMapper.readValue(new File(fileName), Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}