package com.Sujal_Industries.AndroidMarket;

import androidx.annotation.Keep;

import com.orm.*;

@Keep
public class Orders extends SugarRecord {
    String user;
    String details;
    String date;
    int price;

    public Orders() {

    }

    public Orders(String user, String details, String date, int price) {
        this.user = user;
        this.details = details;
        this.date = date;
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
