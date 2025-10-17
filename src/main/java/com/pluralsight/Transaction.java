package com.pluralsight;

// Class to store transaction data
public class Transaction {
    private String date;        // Transaction date
    private String time;        // Transaction time
    private String description; // Description of transaction
    private String vendor;      // Vendor or source
    private double amount;      // Amount (positive = deposit, negative = payment)

    // Constructor to create a new transaction
    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getter for amount
    public double getAmount() { return amount; }

    // Getter for vendor
    public String getVendor() { return vendor; }

    // Convert transaction to a CSV string (for saving)
    public String toCsvString() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    // Print transaction nicely
    @Override
    public String toString() {
        return date + " " + time + " | " + description + " | " + vendor + " | " + amount;
    }

    // Getter for date (needed for reports)
    public String getDate() { return date; }
}
