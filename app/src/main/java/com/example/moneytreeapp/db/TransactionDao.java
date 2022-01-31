package com.example.moneytreeapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.moneytreeapp.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void upsert(Transaction transaction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTransaction(List<Transaction> transactions);

    @Delete
    void delete(Transaction transaction);

    @Query("delete from `transaction`")
    void deleteAllAccounts();

    @Query("select * from `transaction` order by date DESC")
    LiveData<List<Transaction>> getAllAccounts();
}
