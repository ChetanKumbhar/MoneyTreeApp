package com.example.moneytreeapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moneytreeapp.model.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void upsert(Account account);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAccounts(List<Account> accounts);

    @Delete
    void delete(Account account);

    @Query("delete from account")
    void deleteAllAccounts();

    @Query("select * from account order by name ASC")
    LiveData<List<Account>> getAllAccounts();

    @Query("select * from account where id = :id")
    LiveData<Account> getAccountById(int id);

    @Query("select sum(current_balance_in_base) from account" )
    LiveData<Integer> getTotalAmount();
}
