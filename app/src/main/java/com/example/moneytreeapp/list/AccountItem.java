package com.example.moneytreeapp.list;


import com.example.moneytreeapp.model.Account;

public class AccountItem extends ListItem {
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int getType() {
        return TYPE_ACCOUNT;
    }


}