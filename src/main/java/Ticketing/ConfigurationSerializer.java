package Ticketing;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ConfigurationSerializer {

    public static void saveConfiguration(Configuration config, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(config);
            System.out.println("Configuration saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSettingsToFile(Configuration config, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Total Tickets: " + config.getTotalTickets() + "\n");
            writer.write("Ticket Release Rate: " + config.getTicketReleaseRate() + "\n");
            writer.write("Customer Retrieval Rate: " + config.getCustomerRetrievalRate() + "\n");
            writer.write("Max Ticket Capacity: " + config.getMaxTicketCapacity() + "\n");
            System.out.println("Settings written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}