package com.example.moneytreeapp.repository;

import android.content.Context;
import com.example.moneytreeapp.list.AccountItem;
import com.example.moneytreeapp.list.HeaderItem;
import com.example.moneytreeapp.list.ListItem;
import com.example.moneytreeapp.list.TransactionItem;
import com.example.moneytreeapp.model.Account;
import com.example.moneytreeapp.model.AccountList;
import com.example.moneytreeapp.model.Transaction;
import com.example.moneytreeapp.model.TransactionList;
import com.example.moneytreeapp.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * this class will be used for parse the JSON to required objects
 * Also used to convert List from DB to list required for UI
 *
 */
public class JSONRepository {

    public Observable<List<Account>> getAllAccountsFromJOSN(Context context){
        String accountListString = Utils.getJsonFromAssets(context, "accounts.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<AccountList>() { }.getType();
        AccountList list = gson.fromJson(accountListString, listUserType);
        if(list != null)
        return Observable.fromArray(list.getAccounts());

        return null;
    }

    public Observable<List<Transaction>> getAllTransactionFromJOSN(Context context,int accountID){
        String transactionListString = Utils.getJsonFromAssets(context, "transactions_"+accountID+".json");

        Gson gson = new Gson();
        Type listUserType = new TypeToken<TransactionList>() { }.getType();
        TransactionList list = gson.fromJson(transactionListString, listUserType);
        if(list != null)
        return Observable.fromArray(list.getTransactions());

        return null;
    }

    public List<ListItem> convertAccountsToListFormat(List<Account> list){

        HashMap<String, List<Account>> groupedHashMap = groupAccountIntoHashMap(list);
        List<ListItem> consolidatedList = new ArrayList<>();

        for (String header : groupedHashMap.keySet()) {
            HeaderItem headerItem = new HeaderItem();
            headerItem.setHeader(header);
            consolidatedList.add(headerItem);
            for (Account account : groupedHashMap.get(header)) {
                AccountItem accountItem = new AccountItem();
                accountItem.setAccount(account);//setBookingDataTabs(bookingDataTabs);
                consolidatedList.add(accountItem);
            }
        }
        return consolidatedList;
    }

    private HashMap<String, List<Account>> groupAccountIntoHashMap(List<Account> list) {
        HashMap<String, List<Account>> groupedHashMap = new HashMap<>();
        for (Account account : list) {
            String hashMapKey = account.getInstitution();
            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add against the existing key.
                groupedHashMap.get(hashMapKey).add(account);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                List<Account> accountList = new ArrayList<>();
                accountList.add(account);
                groupedHashMap.put(hashMapKey, accountList);
            }
        }
        return groupedHashMap;
    }

    public List<ListItem> convertTransactionToListFormat(List<Transaction> list){
        HashMap<String, List<Transaction>> groupedHashMap = groupTransactionsIntoHashMap(list);
        List<ListItem> consolidatedList = new ArrayList<>();

        for (String header : groupedHashMap.keySet()) {
            HeaderItem headerItem = new HeaderItem();
            headerItem.setHeader(header);
            consolidatedList.add(headerItem);
            for (Transaction transaction : groupedHashMap.get(header)) {
                TransactionItem accountItem = new TransactionItem();
                accountItem.setTransaction(transaction);//setBookingDataTabs(bookingDataTabs);
                consolidatedList.add(accountItem);
            }
        }
        return consolidatedList;
    }


    private HashMap<String, List<Transaction>> groupTransactionsIntoHashMap(List<Transaction> list) {
        HashMap<String, List<Transaction>> groupedHashMap = new HashMap<>();
        for (Transaction transaction : list) {
            Date date = transaction.getDate();
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
            String hashMapKey  = formatter.format(date);

            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add against the existing key.
                groupedHashMap.get(hashMapKey).add(transaction);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                List<Transaction> transactionList = new ArrayList<>();
                transactionList.add(transaction);
                groupedHashMap.put(hashMapKey, transactionList);
            }
        }
        return groupedHashMap;
    }
}
