package com.example.moneytreeapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.moneytreeapp.db.AccountDao;
import com.example.moneytreeapp.db.AccountDatabase;
import com.example.moneytreeapp.model.Account;


import java.util.List;

public class AccountRepository {
    public AccountDao accountDao;
    private LiveData<List<Account>> accounts;
    private LiveData<Integer> totalAmount;


    public AccountRepository(Application application){
        AccountDatabase accountDatabase = AccountDatabase.getInstance(application);
        accountDao = accountDatabase.accountDao();
        accounts = accountDao.getAllAccounts();
        totalAmount = accountDao.getTotalAmount();
    }

    public void upsert(Account account){
    }

    public void insertAllAccounts(List<Account> account){
        new InsertAsyncTask(accountDao).execute(account);
    }

    public LiveData<List<Account>> getAccounts(){
        return this.accounts;
    }

    public LiveData<Integer> getTotalAmount() {
        return totalAmount;
    }

    public LiveData<Account> getAccountByID(int accountID) {
        return accountDao.getAccountById(accountID);
    }

    private static class InsertAsyncTask extends AsyncTask<List<Account>,Void,Void>{
        private AccountDao accountDao;

        public InsertAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(List<Account>... lists) {
            accountDao.insertAllAccounts(lists[0]);
            return null;
        }
    }
}
