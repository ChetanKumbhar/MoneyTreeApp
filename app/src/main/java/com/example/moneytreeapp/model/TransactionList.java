package com.example.moneytreeapp.model;

import com.example.moneytreeapp.model.Transaction;

import java.util.ArrayList;

public class TransactionList {
    public ArrayList<Transaction> transactions;

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
