package JavaCLI;

import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationSerializer {

    public static void saveConfiguration(Configuration config, String fileName) {
        String jsonData = String.format(
                "{\n" +
                        "  \"totalTickets\": %d,\n" +
                        "  \"ticketReleaseRate\": %d,\n" +
                        "  \"customerRetrievalRate\": %d,\n" +
                        "  \"maxTicketCapacity\": %d,\n" +
                        "  \"numberOfCustomers\": %d,\n" +
                        "  \"numberOfVendors\": %d\n" +
                        "}",
                config.getTotalTickets(),
                config.getTicketReleaseRate(),
                config.getCustomerRetrievalRate(),
                config.getMaxTicketCapacity(),
                config.getNumCustomers(),
                config.getNumVendors()
        );

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonData);
            System.out.println("Configuration saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}