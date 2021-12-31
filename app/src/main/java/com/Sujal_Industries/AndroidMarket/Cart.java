package com.Sujal_Industries.AndroidMarket;

import com.orm.*;

import java.io.*;

public class Cart extends SugarRecord implements Serializable {
    long item;
    String user;
    int number;

    public Cart() {

    }

    public Cart(long item, String user, int number) {
        this.item = item;
        this.user = user;
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setItem(long item) {
        this.item = item;
    }

    public long getItem() {
        return item;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public Items getItemOb() {
        return Items.findById(Items.class, item);
    }
}
