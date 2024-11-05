package Ticketing;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicketPool {
    private final List<String> tickets = new CopyOnWriteArrayList<>();

    public synchronized void addTickets(List<String> newTickets) {
        tickets.addAll(newTickets);
        System.out.println("Added " + newTickets.size() + " tickets to the pool.");
    }

    public synchronized String removeTicket() {
        if (!tickets.isEmpty()) {
            String ticket = tickets.remove(0);
            System.out.println("Removed ticket: " + ticket);
            return ticket;
        } else {
            System.out.println("No tickets available to remove.");
            return null;
        }
    }

    public List<String> getTickets() {
        return new CopyOnWriteArrayList<>(tickets);
    }
}