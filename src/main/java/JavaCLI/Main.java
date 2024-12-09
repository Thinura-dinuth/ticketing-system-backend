package JavaCLI;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private TicketPool ticketPool;
    private List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) {
        Main mainInstance = new Main();
        try {
            mainInstance.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int totalTickets = getInput(scanner, "Enter Total Number of Tickets: ", "Total Tickets must be a positive number.");
        int ticketReleaseRate = getInput(scanner, "Enter Ticket Release Rate (tickets per minute): ", "Ticket Release Rate must be a positive number.");
        int customerRetrievalRate = getInput(scanner, "Enter Customer Retrieval Rate (tickets per minute): ", "Customer Retrieval Rate must be a positive number.");
        int maxTicketCapacity = getInput(scanner, "Enter Max Ticket Capacity: ", "Max Ticket Capacity must be a positive number and greater than or equal to Total Tickets.", totalTickets);
        int numVendors = getInput(scanner, "Enter Number of Vendors: ", "Number of Vendors must be a positive number.");
        int numCustomers = getInput(scanner, "Enter Number of Customers: ", "Number of Customers must be a positive number.");

        Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity, numVendors, numCustomers);
        ConfigurationSerializer.saveConfiguration(config, "config.txt");
        System.out.println("Configuration set successfully.");

        ticketPool = new TicketPool(maxTicketCapacity);

        // Add the total number of tickets to the ticket pool
        List<String> initialTickets = new ArrayList<>();
        for (int i = 0; i < totalTickets; i++) {
            initialTickets.add("Ticket-" + i);
        }

        ticketPool.addTickets(initialTickets);

        menu(scanner, config, numVendors, numCustomers);
    }

    private int getInput(Scanner scanner, String prompt, String errorMessage) {
        int input;
        while (true) {
            try {
                System.out.print(prompt);
                input = scanner.nextInt();
                if (input > 0) break;
                System.out.println(errorMessage);
            } catch (InputMismatchException e) {
                System.out.println(errorMessage);
                scanner.next(); // Clear invalid input
            }
        }
        return input;
    }

    private int getInput(Scanner scanner, String prompt, String errorMessage, int minValue) {
        int input;
        while (true) {
            try {
                System.out.print(prompt);
                input = scanner.nextInt();
                if (input > 0 && input >= minValue) break;
                System.out.println(errorMessage);
            } catch (InputMismatchException e) {
                System.out.println(errorMessage);
                scanner.next(); // Clear invalid input
            }
        }
        return input;
    }

    private void menu(Scanner scanner, Configuration config, int numVendors, int numCustomers) {
        while (true) {
            System.out.print("Enter command (start-1/stop-2/add-3/remove-4/exit-0): ");
            String command = scanner.next();

            try {
                if (command.equalsIgnoreCase("start") || command.equals("1")) {
                    start(config, numVendors, numCustomers);
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

    private void start(Configuration config, int numVendors, int numCustomers) {
        System.out.println("Starting operations...");

        for (int i = 1; i <= numVendors; i++) {
            Vendor vendor = new Vendor("Vendor" + i, i, config.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendor);
            threads.add(vendorThread);
            vendorThread.start();
        }

        for (int i = 1; i <= numCustomers; i++) {
            Customer customer = new Customer("Customer" + i, i, config.getCustomerRetrievalRate(), ticketPool);
            Thread customerThread = new Thread(customer);
            threads.add(customerThread);
            customerThread.start();
        }
    }

    private void stop() {
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