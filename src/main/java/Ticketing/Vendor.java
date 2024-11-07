package Ticketing;

import java.util.ArrayList;
import java.util.List;

public class Vendor implements Runnable {
    private String name;
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;
    private TicketPool ticketPool;

    public Vendor(String name, int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.name = name;
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        System.out.println(name + " is running.");
        while (true) {
            List<String> newTickets = new ArrayList<>();
            for (int i = 0; i < ticketsPerRelease; i++) {
                newTickets.add("Ticket-" + vendorId + "-" + System.currentTimeMillis());
            }
            ticketPool.addTickets(newTickets);
            System.out.println(name + " released " + ticketsPerRelease + " tickets.");

            try {
                Thread.sleep(releaseInterval); // Simulate time taken to release tickets
            } catch (InterruptedException e) {
                System.out.println(name + " was interrupted.");
                break;
            }
        }
        System.out.println(name + " has finished releasing tickets.");
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public int getReleaseInterval() {
        return releaseInterval;
    }

    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }
}