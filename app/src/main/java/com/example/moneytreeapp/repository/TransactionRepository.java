package com.example.moneytreeapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.moneytreeapp.db.AccountDatabase;
import com.example.moneytreeapp.db.TransactionDao;
import com.example.moneytreeapp.model.Transaction;

import java.util.List;

public class TransactionRepository {
    public TransactionDao transactionDao;
    private LiveData<List<Transaction>> transactions;

    public TransactionRepository(Application application){
        AccountDatabase accountDatabase = AccountDatabase.getInstance(application);
        transactionDao = accountDatabase.transactionDao();
        transactions = transactionDao.getAllAccounts();
    }

    public void upsert(Transaction transaction){
    }

    public void insertAllTransaction(List<Transaction> transactions){
        new InsertTransactionAsyncTask(transactionDao).execute(transactions);
    }

    public LiveData<List<Transaction>> getTransactions(){
        return this.transactions;
    }

    private class InsertTransactionAsyncTask extends AsyncTask<List<Transaction>,Void,Void>{
        private TransactionDao transactionDao;

        public InsertTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(List<Transaction>... transactions) {
            transactionDao.insertAllTransaction(transactions[0]);
            return null;
        }
    }
}
