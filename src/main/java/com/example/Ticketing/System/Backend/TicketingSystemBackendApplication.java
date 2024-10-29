package com.example.Ticketing.System.Backend;

import Ticketing.Configuration;
import Ticketing.ConfigurationSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class TicketingSystemBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TicketingSystemBackendApplication.class, args);
	}

	@Override
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

		Configuration tickets = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
		System.out.println("Configuration set successfully.");

		ConfigurationSerializer.saveConfiguration(tickets, "configuration.ser");
		ConfigurationSerializer.writeSettingsToFile(tickets, "settings.txt");

		System.out.println("Total Tickets: " + tickets.getTotalTickets());
		System.out.println("Ticket Release Rate: " + tickets.getTicketReleaseRate());
		System.out.println("Customer Retrieval Rate: " + tickets.getCustomerRetrievalRate());
		System.out.println("Max Ticket Capacity: " + tickets.getMaxTicketCapacity());

		scanner.close();
	}
}