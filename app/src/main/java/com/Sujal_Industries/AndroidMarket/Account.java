package com.Sujal_Industries.AndroidMarket;

import com.orm.*;
import com.google.firebase.database.*;

@IgnoreExtraProperties
public class Account extends SugarRecord {
    String name;
    String password;
    String user;
    int balance;

    public Account() {
    }

    public Account(String name, String user, String password, int balance) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.balance = balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUser_name(String user) {
        this.user = user;
    }

    public String getUser_name() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
