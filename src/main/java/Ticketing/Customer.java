package Ticketing;

import java.util.List;

public class Customer implements Runnable {
    private String name;
    private int customerId;
    private int ticketsPerMinute;
    private TicketPool ticketPool;

    public Customer(String name, int customerId, int ticketsPerMinute, TicketPool ticketPool) {
        this.name = name;
        this.customerId = customerId;
        this.ticketsPerMinute = ticketsPerMinute;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        System.out.println(name + " is running.");
        int retrievalInterval = 60000 / ticketsPerMinute; // Convert tickets per minute to interval in milliseconds
        while (true) {
            synchronized (ticketPool) {
                if (ticketPool.getTickets().isEmpty()) {
                    System.out.println(name + " found no more tickets to retrieve.");
                    break;
                }
                List<String> retrievedTickets = ticketPool.removeTickets(1); // Attempt to retrieve one ticket
                if (!retrievedTickets.isEmpty()) {
                    System.out.println(name + " retrieved ticket: " + retrievedTickets.get(0));
                }
                try {
                    Thread.sleep(retrievalInterval); // Simulate time taken to retrieve a ticket
                } catch (InterruptedException e) {
                    System.out.println(name + " was interrupted.");
                    break;
                }
            }
        }
        System.out.println(name + " has finished retrieving tickets.");
    }
}