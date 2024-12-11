# Ticket Management System

## Overview

This Ticket Management System is a Java-based application designed to simulate a complex ticket distribution and management ecosystem. The system manages a shared ticket pool with multiple vendors and customers interacting concurrently, demonstrating advanced multi-threading and synchronization techniques.

## Key Features

 * Dynamic Configuration: Allows runtime configuration of system parameters
 * Concurrent Ticket Management: Supports multiple vendors and customers
 * Real-time Logging: WebSocket-based log streaming
 * Flexible Operations:
     * Start/Stop system operations
     * Add or remove tickets dynamically
     * Configure system parameters interactively

## Prerequisites
 * Java 23
 * Spring Boot
 * React GUI (Github Link - https://github.com/Thinura-dinuth/ticketing-system-react)

## Running the Application

### 1. Open ticketing-system-backend project folder.<br/>
   Download and extract the zip file.<br/>
### 2. Find TicketingSystemBackendApplication. <br/>
(Ticketing-System-Backend\src\main\java\com\example\Ticketing)<br/>
### 3. Run the TicketingSystemBackendApplication. <br/>
Rightclick and select run or you can open the TicketingSystemBackendApplication.java and select run from the top of the screen.<br/>
### 4. Launch the GUI <br/>
(Instructions - https://github.com/Thinura-dinuth/ticketing-system-react/blob/main/README.md)

# Java Command-Line Interface (CLI)
## Purpose
The Java CLI serves as the core control mechanism for the Ticket Management System, providing an interactive interface for system configuration and operational control.

## Key Functionalities

### 1. Initial Configuration

* Prompts user for system parameters on first run
* Validates input for:
    * Total Tickets
    * Ticket release rate
    * Customer retrieval rate
    * Maximum ticket capacity
    * Number of vendors and customers

### 2. Configuration Persistence

* Saves configuration to config.txt
* Allows reloading of previous configurations
* Supports dynamic reconfiguration

### 3. Interactive Command Menu
Available commands:

* start (1): Initiate system operations
* stop (2): Halt all ongoing operations
* add (3): Dynamically add tickets to the pool
* remove (4): Remove tickets from the pool
* exit (0): Terminate the application and clear configuration

  
