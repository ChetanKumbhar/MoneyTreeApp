package com.example.moneytreeapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.moneytreeapp.model.Account;
import com.example.moneytreeapp.model.Transaction;

@Database(entities = {Account.class, Transaction.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase {
    private static AccountDatabase instance;
    public abstract AccountDao accountDao();
    public abstract TransactionDao transactionDao();

    public static synchronized AccountDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, AccountDatabase.class, "account_database").
                    fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
