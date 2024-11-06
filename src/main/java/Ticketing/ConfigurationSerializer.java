package Ticketing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfigurationSerializer {

    public static void saveConfiguration(Configuration config, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Total Tickets: " + config.getTotalTickets());
            writer.println("Ticket Release Rate: " + config.getTicketReleaseRate());
            writer.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
            writer.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
            System.out.println("Configuration saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration loadConfiguration(String fileName) {
        // Implement loading logic if needed
        return null;
    }
}