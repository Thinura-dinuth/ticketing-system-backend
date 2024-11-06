package Ticketing;

import java.util.List;

public class Customer implements Runnable {
    private String name;
    private int customerId;
    private int retrievalInterval;
    private TicketPool ticketPool;

    public Customer(String name, int customerId, int retrievalInterval, TicketPool ticketPool) {
        this.name = name;
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        System.out.println(name + " is running.");
        while (true) {
            synchronized (ticketPool) {
                if (ticketPool.getTickets().isEmpty()) {
                    System.out.println(name + " found no more tickets to retrieve.");
                    break;
                }
                List<String> retrievedTickets = ticketPool.removeTickets(1); // Attempt to retrieve one ticket
                System.out.println(name + " retrieved ticket: " + retrievedTickets.get(0));
            }
            try {
                Thread.sleep(retrievalInterval); // Simulate time taken to retrieve a ticket
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " was interrupted.");
                break;
            }
        }
        System.out.println(name + " has finished retrieving tickets.");
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }
}