package Ticketing;

import java.util.Scanner;

public class Main {
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
        System.out.println("Configuration set successfully.");

        scanner.close();

        TicketPool ticketPool = new TicketPool();

        // Create and start multiple vendor threads
        Vendor vendor1 = new Vendor("Vendor1", 1, 5, 1000);
        Vendor vendor2 = new Vendor("Vendor2", 2, 5, 1000);
        Vendor vendor3 = new Vendor("Vendor3", 3, 5, 1000);

        Thread vendorThread1 = new Thread(vendor1);
        Thread vendorThread2 = new Thread(vendor2);
        Thread vendorThread3 = new Thread(vendor3);

        vendorThread1.start();
        vendorThread2.start();
        vendorThread3.start();

        // Create and start multiple customer threads
        Customer customer1 = new Customer("Customer1", 1, 1000, ticketPool);
        Customer customer2 = new Customer("Customer2", 2, 1000, ticketPool);
        Customer customer3 = new Customer("Customer3", 3, 1000, ticketPool);

        Thread customerThread1 = new Thread(customer1);
        Thread customerThread2 = new Thread(customer2);
        Thread customerThread3 = new Thread(customer3);

        customerThread1.start();
        customerThread2.start();
        customerThread3.start();
    }
}