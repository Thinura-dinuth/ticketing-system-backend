package Ticketing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private TicketPool ticketPool;

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

        int totalTickets;
        while (true) {
            System.out.print("Enter Total Number of Tickets: ");
            totalTickets = scanner.nextInt();
            if (totalTickets > 0) break;
            System.out.println("Total Tickets must be a positive number.");
        }

        int ticketReleaseRate;
        while (true) {
            System.out.print("Enter Ticket Release Rate: ");
            ticketReleaseRate = scanner.nextInt();
            if (ticketReleaseRate > 0) break;
            System.out.println("Ticket Release Rate must be a positive number.");
        }

        int customerRetrievalRate;
        while (true) {
            System.out.print("Enter Customer Retrieval Rate: ");
            customerRetrievalRate = scanner.nextInt();
            if (customerRetrievalRate > 0) break;
            System.out.println("Customer Retrieval Rate must be a positive number.");
        }

        int maxTicketCapacity;
        while (true) {
            System.out.print("Enter Max Ticket Capacity: ");
            maxTicketCapacity = scanner.nextInt();
            if (maxTicketCapacity > 0 && maxTicketCapacity >= totalTickets) break;
            System.out.println("Max Ticket Capacity must be a positive number and greater than or equal to Total Tickets.");
        }

        Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        ConfigurationSerializer.saveConfiguration(config, "config.txt");
        System.out.println("Configuration set successfully.");

        ticketPool = new TicketPool(maxTicketCapacity);

        // Add the total number of tickets to the ticket pool
        List<String> initialTickets = new ArrayList<>();
        for (int i = 0; i < totalTickets; i++) {
            initialTickets.add("Ticket-" + i);
        }

        ticketPool.addTickets(initialTickets);

        menu(scanner, config);
    }

    private void menu(Scanner scanner, Configuration config) {
        while (true) {
            System.out.print("Enter command (start/stop/add/remove/exit): ");
            String command = scanner.next();

            // Have implement the Error Handling part for the menu

            if (command.equalsIgnoreCase("start")) {
                start(config);
            } else if (command.equalsIgnoreCase("stop")) {
                stop();
            } else if (command.equalsIgnoreCase("add")) {
                System.out.print("Enter number of tickets to add: ");
                int numTickets = scanner.nextInt();
                addTickets(numTickets);
            } else if (command.equalsIgnoreCase("remove")) {
                System.out.print("Enter number of tickets to remove: ");
                int numTickets = scanner.nextInt();
                removeTickets(numTickets);
            } else if (command.equalsIgnoreCase("exit")) {
                stop();
                System.out.println("Exiting program.");
                break;
            } else {
                System.out.println("Unknown command. Please enter 'start', 'stop', 'add', 'remove', or 'exit'.");
            }
        }
    }

    private void start(Configuration config) {
        System.out.println("Operations started.");
    }

    private void stop() {
        System.out.println("Operations stopped.");
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
}