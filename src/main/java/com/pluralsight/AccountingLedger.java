package com.pluralsight;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountingLedger {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n==== HOME MENU ==== ");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "D": addDeposit(scanner); break;
                case "P": addPayment(scanner); break;
                case "L": showLedgerMenu(scanner); break;
                case "X":
                    System.out.println("Exiting program...");
                    running = false;
                    break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    // Add Deposit
    public static void addDeposit(Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount < 0) amount = -amount;

        String date = LocalDate.now().toString();
        String time = java.time.LocalTime.now().toString();

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            bw.write(transaction.toCsvString());
            bw.newLine();
            System.out.println("Deposit added successfully!");
        } catch (Exception e) {
            System.out.println("Something went wrong while saving the deposit.");
            e.printStackTrace();
        }
    }

    // Add Payment
    public static void addPayment(Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > 0) amount = -amount;

        String date = LocalDate.now().toString();
        String time = java.time.LocalTime.now().toString();

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            bw.write(transaction.toCsvString());
            bw.newLine();
            System.out.println("Payment recorded successfully!");
        } catch (Exception e) {
            System.out.println("Something went wrong while saving the payment.");
            e.printStackTrace();
        }
    }

    // Ledger Menu
    public static void showLedgerMenu(Scanner scanner) {
        boolean inLedger = true;

        while (inLedger) {
            System.out.println("\n==== LEDGER MENU ====");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");

            String ledgerChoice = scanner.nextLine().toUpperCase();

            switch (ledgerChoice) {
                case "A":
                    System.out.println("\n==== ALL ENTRIES ====");
                    for (Transaction t : readTransactions()) System.out.println(t);
                    break;
                case "D":
                    System.out.println("\n==== DEPOSITS ONLY ====");
                    for (Transaction t : readTransactions()) if (t.getAmount() > 0) System.out.println(t);
                    break;
                case "P":
                    System.out.println("\n==== PAYMENTS ONLY ====");
                    for (Transaction t : readTransactions()) if (t.getAmount() < 0) System.out.println(t);
                    break;
                case "R":
                    showReportsMenu(scanner);
                    break;
                case "H":
                    inLedger = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Reports Menu
    public static void showReportsMenu(Scanner scanner) {
        boolean inReports = true;
        ArrayList<Transaction> allTransactions = readTransactions();

        while (inReports) {
            System.out.println("\n==== REPORTS MENU ====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String reportChoice = scanner.nextLine();

            LocalDate today = LocalDate.now();
            switch (reportChoice) {
                case "1": // Month To Date
                    System.out.println("\n==== MONTH TO DATE ====");
                    for (Transaction t : allTransactions) {
                        LocalDate d = LocalDate.parse(t.getDate());
                        if (d.getMonth() == today.getMonth() && d.getYear() == today.getYear()) System.out.println(t);
                    }
                    break;
                case "2": // Previous Month
                    System.out.println("\n==== PREVIOUS MONTH ====");
                    for (Transaction t : allTransactions) {
                        LocalDate d = LocalDate.parse(t.getDate());
                        LocalDate prevMonth = today.minusMonths(1);
                        if (d.getMonth() == prevMonth.getMonth() && d.getYear() == prevMonth.getYear()) System.out.println(t);
                    }
                    break;
                case "3": // Year To Date
                    System.out.println("\n==== YEAR TO DATE ====");
                    for (Transaction t : allTransactions) {
                        LocalDate d = LocalDate.parse(t.getDate());
                        if (d.getYear() == today.getYear()) System.out.println(t);
                    }
                    break;
                case "4": // Previous Year
                    System.out.println("\n==== PREVIOUS YEAR ====");
                    for (Transaction t : allTransactions) {
                        LocalDate d = LocalDate.parse(t.getDate());
                        if (d.getYear() == today.getYear() - 1) System.out.println(t);
                    }
                    break;
                case "5": // Search by Vendor
                    System.out.print("Enter vendor name to search: ");
                    String vendorSearch = scanner.nextLine().toLowerCase();
                    System.out.println("\n==== SEARCH RESULTS ====");
                    for (Transaction t : allTransactions) {
                        if (t.getVendor().toLowerCase().contains(vendorSearch)) System.out.println(t);
                    }
                    break;
                case "0":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Read transactions from CSV
    public static ArrayList<Transaction> readTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String date = parts[0];
                    String time = parts[1];
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }
        } catch (Exception e) {
            System.out.println("Could not read transactions.csv or file is empty.");
        }
        Collections.reverse(transactions);
        return transactions;
    }
}
