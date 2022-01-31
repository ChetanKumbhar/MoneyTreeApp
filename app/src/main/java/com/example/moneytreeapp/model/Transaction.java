package com.example.moneytreeapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.moneytreeapp.db.DateConverter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "transaction")

public class Transaction {

    @SerializedName("account_id")
    @ColumnInfo(name = "account_id")
    private int accountId;
    private double amount;
    @SerializedName("category_id")
    @ColumnInfo(name = "category_id")
    private int categoryID;
    @TypeConverters(DateConverter.class)
    private Date date;
    private String description;
    @PrimaryKey
    private int id;

    @Override
    public String toString() {
        return "transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", categoryID=" + categoryID +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
