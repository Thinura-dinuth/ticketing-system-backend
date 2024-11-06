package Ticketing;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final List<String> tickets = new ArrayList<>();
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTickets(List<String> newTickets) {
        if (tickets.size() + newTickets.size() <= maxCapacity) {
            tickets.addAll(newTickets);
            System.out.println("Added " + newTickets.size() + " tickets to the pool.");
        } else {
            System.out.println("Cannot add tickets. Maximum capacity reached.");
        }
        displayTicketStatus();
    }

    public synchronized List<String> removeTickets(int numberOfTickets) {
        List<String> removedTickets = new ArrayList<>();
        for (int i = 0; i < numberOfTickets && !tickets.isEmpty(); i++) {
            removedTickets.add(tickets.remove(0));
        }
        System.out.println("Removed " + removedTickets.size() + " tickets from the pool.");
        displayTicketStatus();
        return removedTickets;
    }

    public synchronized List<String> getTickets() {
        return new ArrayList<>(tickets);
    }

    private void displayTicketStatus() {
        System.out.println("Current ticket pool status: " + tickets.size() + " tickets available.");
    }
}