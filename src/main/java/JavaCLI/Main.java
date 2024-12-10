package JavaCLI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private TicketPool ticketPool;
    private List<Thread> threads = new ArrayList<>();
    private Configuration config;

    public static void main(String[] args) {
        Main mainInstance = new Main();
        try {
            mainInstance.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(String... args) throws Exception {
        // Check if the config file is empty
        if (isConfigFileEmpty("config.txt")) {
            // If empty, prompt user for configuration
            config = promptUserForConfiguration();
            // Save the new configuration to the file
            ConfigurationSerializer.saveConfiguration(config, "config.txt");
            // Initialize ticket pool using max ticket capacity from config
            ticketPool = new TicketPool(config.getMaxTicketCapacity());

            // Add the total number of tickets to the ticket pool
            List<String> initialTickets = new ArrayList<>();
            for (int i = 0; i < config.getTotalTickets(); i++) {
                initialTickets.add("Ticket-" + i);
            }

            ticketPool.addTickets(initialTickets);
            System.out.println("Configuration set successfully.");
            menu(config);
        } else {
            // If not empty, load configuration from the file
            config = loadConfiguration("config.txt");
            if (config == null) {
                System.out.println("Failed to load configuration. Please check the config file.");
                return;
            }
            // Initialize ticket pool using max ticket capacity from config
            ticketPool = new TicketPool(config.getMaxTicketCapacity());

            // Add the total number of tickets to the ticket pool
            List<String> initialTickets = new ArrayList<>();
            for (int i = 0; i < config.getTotalTickets(); i++) {
                initialTickets.add("Ticket-" + i);
            }
            ticketPool.addTickets(initialTickets);
            System.out.println("Configuration loaded from config.txt.");
            start(config);
        }
    }

    private boolean isConfigFileEmpty(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public Configuration loadConfiguration(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line.trim());
            }

            String content = fileContent.toString();
            int totalTickets = extractValue(content, "totalTickets");
            int ticketReleaseRate = extractValue(content, "ticketReleaseRate");
            int customerRetrievalRate = extractValue(content, "customerRetrievalRate");
            int maxTicketCapacity = extractValue(content, "maxTicketCapacity");
            int numCustomers = extractValue(content, "numberOfCustomers");
            int numVendors = extractValue(content, "numberOfVendors");

            return new Configuration(
                    totalTickets,
                    ticketReleaseRate,
                    customerRetrievalRate,
                    maxTicketCapacity,
                    numVendors,
                    numCustomers
            );
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int extractValue(String content, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = content.indexOf(searchKey) + searchKey.length();
        int endIndex = content.indexOf(",", startIndex);
        if (endIndex == -1) { // Last element case
            endIndex = content.indexOf("}", startIndex);
        }
        String value = content.substring(startIndex, endIndex).trim();
        return Integer.parseInt(value);
    }

    private Configuration promptUserForConfiguration() {
        Scanner scanner = new Scanner(System.in);

        int totalTickets = getInput(scanner, "Enter Total Number of Tickets: ", "Total Tickets must be a positive number.", ConfigurationValidator::validateTotalTickets);
        int ticketReleaseRate = getInput(scanner, "Enter Ticket Release Rate (tickets per minute, max 60): ", "Ticket Release Rate must be a positive number and at most 60.", ConfigurationValidator::validateTicketReleaseRate);
        int customerRetrievalRate = getInput(scanner, "Enter Customer Retrieval Rate (tickets per minute, max 60): ", "Customer Retrieval Rate must be a positive number and at most 60.", ConfigurationValidator::validateCustomerRetrievalRate);
        int maxTicketCapacity = getInput(scanner, "Enter Max Ticket Capacity: ", "Max Ticket Capacity must be a positive number and greater than or equal to Total Tickets.", value -> ConfigurationValidator.validateMaxTicketCapacity(value, totalTickets));
        int numVendors = getInput(scanner, "Enter Number of Vendors: ", "Number of Vendors must be a positive number.", ConfigurationValidator::validateNumVendors);
        int numCustomers = getInput(scanner, "Enter Number of Customers: ", "Number of Customers must be a positive number.", ConfigurationValidator::validateNumCustomers);

        return new Configuration(
                totalTickets,
                ticketReleaseRate,
                customerRetrievalRate,
                maxTicketCapacity,
                numVendors,
                numCustomers
        );
    }

    private int getInput(Scanner scanner, String prompt, String errorMessage, java.util.function.IntPredicate validator) {
        int input;
        while (true) {
            try {
                System.out.print(prompt);
                input = scanner.nextInt();
                if (validator.test(input)) break;
                System.out.println(errorMessage);
            } catch (InputMismatchException e) {
                System.out.println(errorMessage);
                scanner.next(); // Clear invalid input
            }
        }
        return input;
    }

    private void menu(Configuration config) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command (start-1/stop-2/add-3/remove-4/exit-0): ");
            String command = scanner.next();

            try {
                if (command.equalsIgnoreCase("start") || command.equals("1")) {
                    start(config);
                } else if (command.equalsIgnoreCase("stop") || command.equals("2")) {
                    stop();
                } else if (command.equalsIgnoreCase("add") || command.equals("3")) {
                    System.out.print("Enter number of tickets to add: ");
                    int numTickets = scanner.nextInt();
                    addTickets(numTickets);
                } else if (command.equalsIgnoreCase("remove") || command.equals("4")) {
                    System.out.print("Enter number of tickets to remove: ");
                    int numTickets = scanner.nextInt();
                    removeTickets(numTickets);
                } else if (command.equalsIgnoreCase("exit") || command.equals("0")) {
                    stop();
                    clearConfigurationFile("config.txt");
                    System.out.println("Exiting program.");
                    break;
                } else {
                    System.out.println("Unknown command. Please enter 'start', 'stop', 'add', 'remove', or 'exit'.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Unknown command. Please enter 'start', 'stop', 'add', 'remove', or 'exit'.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    public void start(Configuration config) {
        System.out.println("Starting operations...");

        // Initialize ticket pool using max ticket capacity from config
        ticketPool = new TicketPool(config.getMaxTicketCapacity());

        // Add the total number of tickets to the ticket pool
        List<String> initialTickets = new ArrayList<>();
        for (int i = 0; i < config.getTotalTickets(); i++) {
            initialTickets.add("Ticket-" + i);
        }
        ticketPool.addTickets(initialTickets);

        for (int i = 1; i <= config.getNumVendors(); i++) {
            Vendor vendor = new Vendor(
                    "Vendor" + i,
                    i,
                    config.getTicketReleaseRate(),
                    ticketPool
            );
            Thread vendorThread = new Thread(vendor);
            threads.add(vendorThread);
        }

        for (int i = 1; i <= config.getNumCustomers(); i++) {
            Customer customer = new Customer(
                    "Customer" + i,
                    i,
                    config.getCustomerRetrievalRate(),
                    ticketPool
            );
            Thread customerThread = new Thread(customer);
            threads.add(customerThread);
        }

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void stop() {
        System.out.println("Stopping operations...");
        for (Thread thread : threads) {
            thread.interrupt();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads.clear();
        System.out.println("All operations stopped.");
    }

    private void addTickets(int numTickets) {
        List<String> newTickets = new ArrayList<>();
        for (int i = 0; i < numTickets; i++) {
            newTickets.add("Ticket-" + System.currentTimeMillis());
        }
        ticketPool.addTickets(newTickets);
    }

    private void removeTickets(int numTickets) {
        ticketPool.removeTickets(numTickets);
    }

    private void clearConfigurationFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}