package com.pluralsight;

import java.util.*;       // For Scanner, ArrayList, Collections
import java.io.*;         // For reading and writing files
import java.time.LocalDate;
import java.time.LocalTime;

// Main class for the Everyday Ledger application
public class AccountingLedger {

    // Main method – program starts here
    public static void main(String[] args) {
        // Create Scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Boolean variable to control the main loop
        boolean running = true;

        // Print program header
        System.out.println("\n==============================");
        System.out.println("      EVERYDAY LEDGER");
        System.out.println("==============================");

        // Main menu loop – keeps running until user exits
        while (running) {
            // Display main menu options
            System.out.println("\n==== EVERYDAY LEDGER MENU ====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            // Read user choice and convert to uppercase
            String choice = scanner.nextLine().toUpperCase();

            // Perform action based on user choice
            switch (choice) {
                case "D": addDeposit(scanner); break;  // Add deposit
                case "P": addPayment(scanner); break;  // Add payment
                case "L": showLedgerMenu(scanner); break; // Show ledger menu
                case "X": // Exit program
                    System.out.println("Exiting program...");
                    running = false;
                    break;
                default: // Invalid input
                    System.out.println("Invalid option. Try again.");
            }
        }

        // Close the scanner when program ends
        scanner.close();
    }

    // Method to add a deposit
    public static void addDeposit(Scanner scanner) {
        // Ask for transaction description
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        // Ask for vendor
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        // Ask for amount
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // Ensure deposit is positive
        if (amount < 0) amount = -amount;

        // Get current date and time
        String date = LocalDate.now().toString();
        String time = LocalTime.now().toString();

        // Create a Transaction object
        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        // Write transaction to CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            bw.write(transaction.toCsvString()); // Write transaction as CSV line
            bw.newLine(); // Move to next line
            System.out.println("Deposit added successfully!");
        } catch (Exception e) {
            System.out.println("Something went wrong while saving the deposit.");
        }
    }

    // Method to add a payment
    public static void addPayment(Scanner scanner) {
        // Ask for transaction description
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        // Ask for vendor
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        // Ask for amount
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // Ensure payment is negative
        if (amount > 0) amount = -amount;

        // Get current date and time
        String date = LocalDate.now().toString();
        String time = LocalTime.now().toString();

        // Create a Transaction object
        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        // Write transaction to CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            bw.write(transaction.toCsvString()); // Write transaction as CSV line
            bw.newLine(); // Move to next line
            System.out.println("Payment recorded successfully!");
        } catch (Exception e) {
            System.out.println("Something went wrong while saving the payment.");
        }
    }

    // Method to show the Ledger menu
    public static void showLedgerMenu(Scanner scanner) {
        // Boolean to keep Ledger menu loop running
        boolean inLedger = true;

        // Ledger menu loop
        while (inLedger) {
            // Display ledger options
            System.out.println("\n==== LEDGER MENU ====");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");

            // Read user choice
            String choice = scanner.nextLine().toUpperCase();

            // Read all transactions from CSV file
            ArrayList<Transaction> transactions = readTransactions();

            // Perform action based on user choice
            switch (choice) {
                case "A": // Show all transactions
                    System.out.println("\n==== ALL ENTRIES ====");
                    for (Transaction t : transactions) System.out.println(t);
                    break;
                case "D": // Show deposits only
                    System.out.println("\n==== DEPOSITS ONLY ====");
                    for (Transaction t : transactions) if (t.getAmount() > 0) System.out.println(t);
                    break;
                case "P": // Show payments only
                    System.out.println("\n==== PAYMENTS ONLY ====");
                    for (Transaction t : transactions) if (t.getAmount() < 0) System.out.println(t);
                    break;
                case "R": // Show reports menu
                    showReportsMenu(scanner, transactions);
                    break;
                case "H": // Go back to main menu
                    inLedger = false;
                    break;
                default: // Invalid input
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Method to show Reports menu
    public static void showReportsMenu(Scanner scanner, ArrayList<Transaction> transactions) {
        // Boolean to keep reports menu running
        boolean inReports = true;

        // Get current date
        LocalDate today = LocalDate.now();

        // Reports menu loop
        while (inReports) {
            // Display report options
            System.out.println("\n==== REPORTS MENU ====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            // Read user choice
            String reportChoice = scanner.nextLine();

            // Show transactions based on selected report
            switch (reportChoice) {
                case "1": // Month To Date
                    System.out.println("\n==== MONTH TO DATE ====");
                    for (Transaction t : transactions) {
                        LocalDate d = LocalDate.parse(t.getDate());
                        if (d.getMonth() == today.getMonth() && d.getYear() == today.getYear()) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "2": // Previous Month
                    System.out.println("\n==== PREVIOUS MONTH ====");
                    LocalDate prevMonth = today.minusMonths(1);
                    for (Transaction t : transactions) {
                        LocalDate d = LocalDate.parse(t.getDate());
                        if (d.getMonth() == prevMonth.getMonth() && d.getYear() == prevMonth.getYear()) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "3": // Year To Date
                    System.out.println("\n==== YEAR TO DATE ====");
                    for (Transaction t : transactions) {
                        LocalDate d = LocalDate.parse(t.getDate());
                        if (d.getYear() == today.getYear()) System.out.println(t);
                    }
                    break;
                case "4": // Previous Year
                    System.out.println("\n==== PREVIOUS YEAR ====");
                    for (Transaction t : transactions) {
                        LocalDate d = LocalDate.parse(t.getDate());
                        if (d.getYear() == today.getYear() - 1) System.out.println(t);
                    }
                    break;
                case "5": // Search by vendor
                    System.out.print("Enter vendor name to search: ");
                    String vendorSearch = scanner.nextLine().toLowerCase();
                    System.out.println("\n==== SEARCH RESULTS ====");
                    for (Transaction t : transactions) {
                        if (t.getVendor().toLowerCase().contains(vendorSearch)) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "0": // Back to Ledger menu
                    inReports = false;
                    break;
                default: // Invalid input
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Method to read transactions from CSV file
    public static ArrayList<Transaction> readTransactions() {
        // Create empty list to store transactions
        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;

            // Read each line from file
            while ((line = br.readLine()) != null) {
                // Split CSV line into parts
                String[] parts = line.split("\\|");
                if (parts.length == 5) { // Make sure line is valid
                    String date = parts[0];
                    String time = parts[1];
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    // Add transaction to list
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }
        } catch (Exception e) {
            System.out.println("Could not read transactions.csv or file is empty.");
        }

        // Reverse list so newest transactions appear first
        Collections.reverse(transactions);

        return transactions;
    }
}
