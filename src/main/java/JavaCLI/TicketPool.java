package JavaCLI;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final List<String> tickets = new ArrayList<>();
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTickets(List<String> newTickets) {
        int availableCapacity = maxCapacity - tickets.size();
        if (availableCapacity == 0) {
            System.out.println("Cannot add tickets. Maximum capacity reached.");
        } else if (newTickets.size() > availableCapacity) {
            System.out.println("You can only add " + availableCapacity + " more tickets.");
        } else {
            tickets.addAll(newTickets);
            System.out.println("Added " + newTickets.size() + " tickets to the pool.");
            displayTicketStatus();
        }
    }

    public synchronized List<String> removeTickets(int numberOfTickets) {
        List<String> removedTickets = new ArrayList<>();
        if (tickets.isEmpty()) {
            System.out.println("No tickets to remove.");
        } else if (numberOfTickets > tickets.size()) {
            System.out.println("Cannot remove " + numberOfTickets + " tickets. Only " + tickets.size() + " available.");
        } else {
            for (int i = 0; i < numberOfTickets; i++) {
                removedTickets.add(tickets.remove(0));
            }
            System.out.println("Removed " + removedTickets.size() + " tickets from the pool.");
            displayTicketStatus();
        }
        return removedTickets;
    }

    public synchronized List<String> getTickets() {
        return new ArrayList<>(tickets);
    }

    private void displayTicketStatus() {
        System.out.println("Current ticket pool status: " + tickets.size() + " tickets available.");
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}