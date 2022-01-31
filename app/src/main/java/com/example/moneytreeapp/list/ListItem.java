package com.example.moneytreeapp.list;

public abstract class ListItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ACCOUNT = 1;
    public static final int TYPE_TRANSACTION = 2;
    abstract public int getType();
}
