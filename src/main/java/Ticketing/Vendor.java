package Ticketing;

public class Vendor implements Runnable {
    private String name;
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;

    public Vendor(String name, int vendorId, int ticketsPerRelease, int releaseInterval) {
        this.name = name;
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        System.out.println(name + " is running.");
        // Sample logic: simulate processing tickets
        for (int i = 0; i < ticketsPerRelease; i++) {
            System.out.println(name + " processed ticket " + (i + 1));
            try {
                Thread.sleep(releaseInterval); // Simulate time taken to process a ticket
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " was interrupted.");
            }
        }
        System.out.println(name + " has finished processing tickets.");
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