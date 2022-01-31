package com.example.moneytreeapp.ui;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moneytreeapp.list.ListItem;
import com.example.moneytreeapp.model.Account;
import com.example.moneytreeapp.model.Transaction;
import com.example.moneytreeapp.repository.AccountRepository;
import com.example.moneytreeapp.repository.JSONRepository;
import com.example.moneytreeapp.repository.TransactionRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountListViewModel extends AndroidViewModel {
    private static final String TAG = "chetan";//""AccountListViewModel";


    // TODO: Implement the ViewModel
    private JSONRepository jsonRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    // list for recyclerView
    private MutableLiveData<List<ListItem>> accountList;
    private MutableLiveData<List<ListItem>> transactionList;


    // list from DB
    private LiveData<List<Account>> accountListDB;
    private LiveData<List<Transaction>> transactionListDB;

    private MutableLiveData<Account> accountData;

    public LiveData<Integer> getTotalAmount() {
        return totalAmount;
    }

    private LiveData<Integer> totalAmount;

    public MutableLiveData<Account> getAccountData() {
        return accountData;
    }




    public AccountListViewModel(@NonNull Application application) {
        super(application);
        this.jsonRepository = new JSONRepository();
        this.accountRepository = new AccountRepository(application);
        this.transactionRepository = new TransactionRepository(application);
        this.accountListDB = accountRepository.getAccounts();
        this.transactionListDB = transactionRepository.getTransactions();
        this.accountList = new MutableLiveData<>();
        this.transactionList = new MutableLiveData<>();
        this.totalAmount = accountRepository.getTotalAmount();
        this.accountData = new MutableLiveData<>();
    }

    public LiveData<List<Account>> getAccountListDB() {
        return accountListDB;
    }



    public LiveData<List<Transaction>> getTransactionListDB() {
        return transactionListDB;
    }




    public MutableLiveData<List<ListItem>> getAccountList() {
        return accountList;
    }

    public MutableLiveData<List<ListItem>> getTransactionList() {
        return transactionList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadAllJsonFromAssets() {
        new Thread(() -> {
            try {
                loadAccountJsonData();
                loadAllTransactionsFromAssets();
            } catch (Exception e) {
                Log.e(TAG, "Error in JSON loading");
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadAllTransactionsFromAssets() throws Exception {
        List<String> list = Arrays.asList(getApplication().getAssets().list(""));
        List<String> listID = list.stream().filter(string -> string.contains("transactions_"))
                .map(s -> s.substring(s.indexOf("_") + 1, s.indexOf(".")))
                .collect(Collectors.toList());

        for (String str : listID) {
            loadTransactionJsonData(Integer.parseInt(str));
        }
    }

    public void loadAccountJsonData() {
        Log.d(TAG, "start loading Account JSON");
        jsonRepository.getAllAccountsFromJOSN(getApplication().getBaseContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accountObserver);


    }

    public void observeAccountList(LifecycleOwner lifecycleOwner) {
        this.getAccountListDB().observe(lifecycleOwner, accounts -> {
            List<ListItem> listItems = jsonRepository.convertAccountsToListFormat(accounts);
            accountList.postValue(listItems);
        });

    }

    public void observeAccountData(LifecycleOwner lifecycleOwner) {
        this.getAccountListDB().observe(lifecycleOwner, accounts -> {
            List<ListItem> listItems = jsonRepository.convertAccountsToListFormat(accounts);
            accountList.postValue(listItems);
        });

    }



    public void loadTransactionJsonData(int accountID) {



        jsonRepository.getAllTransactionFromJOSN(getApplication().getBaseContext(), accountID)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(transactionObserver);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void observeTransactionList(LifecycleOwner lifecycleOwner, int accountID) {
        accountRepository.getAccountByID(accountID).observe(lifecycleOwner, account -> accountData.postValue(account));

        this.getTransactionListDB().observe(lifecycleOwner, accounts -> {
            List<Transaction> listAccountId = accounts.stream()
                    .filter(transaction -> transaction.getAccountId() == accountID)
                    .collect(Collectors.toList());

            List<ListItem> listItems = jsonRepository.convertTransactionToListFormat(listAccountId);
            transactionList.postValue(listItems);
        });
    }

    Observer<List<Account>> accountObserver = new Observer<List<Account>>() {
        @Override
        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

        }

        @Override
        public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Account> accounts) {
            accountRepository.insertAllAccounts(accounts);
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    Observer<List<Transaction>> transactionObserver = new Observer<List<Transaction>>() {
        @Override
        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

        }

        @Override
        public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Transaction> transactions) {
            transactionRepository.insertAllTransaction(transactions);
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };




}