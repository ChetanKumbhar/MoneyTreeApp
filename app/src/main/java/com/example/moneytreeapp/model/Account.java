package com.example.moneytreeapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "account")
public class Account {

    @PrimaryKey
    private int id;
    private String name;
    private String institution;
    private String currency;
    @SerializedName("current_balance")
    @ColumnInfo(name = "current_balance")
    private double currentBalance;
    @ColumnInfo(name = "current_balance_in_base")
    @SerializedName("current_balance_in_base")
    private double currentBalanceInBase;

    public Account(int id, String name, String institution, String currency, double currentBalance, double currentBalanceInBase) {
        this.id = id;
        this.name = name;
        this.institution = institution;
        this.currency = currency;
        this.currentBalance = currentBalance;
        this.currentBalanceInBase = currentBalanceInBase;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", institution='" + institution + '\'' +
                ", currency='" + currency + '\'' +
                ", currentBalance=" + currentBalance +
                ", currentBalanceInBase=" + currentBalanceInBase +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getCurrentBalanceInBase() {
        return currentBalanceInBase;
    }

    public void setCurrentBalanceInBase(double currentBalanceInBase) {
        this.currentBalanceInBase = currentBalanceInBase;
    }
}
