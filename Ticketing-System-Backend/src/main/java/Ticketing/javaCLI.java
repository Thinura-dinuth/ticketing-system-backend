package Ticketing;

import java.util.Scanner;

public class javaCLI {
    public static void main(String[] args) {
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

        Configuration tickets = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        System.out.println("Configuration set successfully.");

        System.out.println("Total Tickets: " + tickets.getTotalTickets());
        System.out.println("Ticket Release Rate: " + tickets.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + tickets.getCustomerRetrievalRate());
        System.out.println("Max Ticket Capacity: " + tickets.getMaxTicketCapacity());


        scanner.close();
    }
}