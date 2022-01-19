package com.Sujal_Industries.AndroidMarket;

import androidx.annotation.Keep;

import com.orm.*;

import java.io.*;

import com.google.firebase.database.*;

@Keep
@IgnoreExtraProperties
public class Items extends SugarRecord implements Serializable {
    String name;
    int price;
    int stock;
    String user;

    public Items() {
    }

    public Items(String name, int price, int stock, String user) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.user = user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
