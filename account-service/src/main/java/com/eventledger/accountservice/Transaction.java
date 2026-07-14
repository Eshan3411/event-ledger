package com.eventledger.accountservice;

import java.time.Instant;
import java.util.Map;

public class Transaction {
    private String transactionId;   // Unique ID for traceability
    private String type;            // CREDIT or DEBIT
    private double amount;          // Must be > 0
    private String currency;        // e.g., USD
    private Instant eventTimestamp; // When the transaction occurred
    private Map<String, String> metadata; // Optional extra info

    public Transaction() {}

    public Transaction(String transactionId, String type, double amount, String currency, Instant eventTimestamp) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.eventTimestamp = eventTimestamp;
    }

    // Getters and setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Instant getEventTimestamp() { return eventTimestamp; }
    public void setEventTimestamp(Instant eventTimestamp) { this.eventTimestamp = eventTimestamp; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }
}
