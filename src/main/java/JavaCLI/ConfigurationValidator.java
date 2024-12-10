package JavaCLI;

public class ConfigurationValidator {

    public static boolean validateTotalTickets(int totalTickets) {
        return totalTickets > 0;
    }

    public static boolean validateTicketReleaseRate(int ticketReleaseRate) {
        return ticketReleaseRate > 0 && ticketReleaseRate <= 60;
    }

    public static boolean validateCustomerRetrievalRate(int customerRetrievalRate) {
        return customerRetrievalRate > 0 && customerRetrievalRate <= 60;
    }

    public static boolean validateMaxTicketCapacity(int maxTicketCapacity, int totalTickets) {
        return maxTicketCapacity > 0 && maxTicketCapacity >= totalTickets;
    }

    public static boolean validateNumVendors(int numVendors) {
        return numVendors > 0;
    }

    public static boolean validateNumCustomers(int numCustomers) {
        return numCustomers > 0;
    }
}