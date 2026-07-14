package com.eventledger.accountservice;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Account {
    @Id
    private String accountId;
    private double balance;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> transactions = new ArrayList<>();

    public Account() {}
    public Account(String accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public List<Event> getTransactions() { return transactions; }
    public void setTransactions(List<Event> transactions) { this.transactions = transactions; }

    public void addTransaction(Event event) { transactions.add(event); }
}
