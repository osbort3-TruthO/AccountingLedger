package com.pluralsight;

import java.util.Scanner;

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
                case "D":
                    System.out.println("Add Deposit (coming soon)");
                    break;

                case "P":
                    System.out.println("Make Payment (coming soon)");
                    break;

                case "L":
                    showLedgerMenu(scanner);  //Correct method call
                    break;

                case "X":
                    System.out.println("Exiting program...");
                    running = false;  //Ends the loop
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    //LEDGER MENU
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
                    System.out.println("Showing all entries (coming soon)");
                    break;

                case "D":
                    System.out.println("Showing deposits only (coming soon)");
                    break;

                case "P":
                    System.out.println("Showing payments only (coming soon)");
                    break;

                case "R":
                    showReportsMenu(scanner);
                    break;

                case "H":
                    inLedger = false;  // Goes back to Home
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ✅ REPORTS MENU
    public static void showReportsMenu(Scanner scanner) {
        boolean inReports = true;

        while (inReports) {
            System.out.println("\n==== REPORTS MENU ====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String reportChoice = scanner.nextLine().toUpperCase();

            switch (reportChoice) {
                case "1":
                    System.out.println("Month To Date (coming soon)");
                    break;
                case "2":
                    System.out.println("Previous Month (coming soon)");
                    break;
                case "3":
                    System.out.println("Year To Date (coming soon)");
                    break;
                case "4":
                    System.out.println("Previous Year (coming soon)");
                    break;
                case "5":
                    System.out.println("Search by Vendor (coming soon)");
                    break;
                case "0":
                    inReports = false;  // Goes back to Ledger Menu
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
