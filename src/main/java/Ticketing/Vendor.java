package Ticketing;

import java.util.ArrayList;
import java.util.List;

public class Vendor implements Runnable {
    private String name;
    private int vendorId;
    private int ticketsPerMinute;
    private TicketPool ticketPool;

    public Vendor(String name, int vendorId, int ticketsPerMinute, TicketPool ticketPool) {
        this.name = name;
        this.vendorId = vendorId;
        this.ticketsPerMinute = ticketsPerMinute;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        System.out.println(name + " is running.");
        int releaseInterval = 60000 / ticketsPerMinute; // Convert tickets per minute to interval in milliseconds
        while (true) {
            List<String> newTickets = new ArrayList<>();
            for (int i = 0; i < ticketsPerMinute; i++) {
                newTickets.add("Ticket-" + vendorId + "-" + System.currentTimeMillis());
            }
            ticketPool.addTickets(newTickets);
            System.out.println(name + " released " + ticketsPerMinute + " tickets.");
            try {
                Thread.sleep(releaseInterval); // Simulate time taken to release tickets
            } catch (InterruptedException e) {
                System.out.println(name + " was interrupted.");
                break;
            }
        }
        System.out.println(name + " has finished releasing tickets.");
    }
}