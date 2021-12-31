package com.Sujal_Industries.AndroidMarket;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.orm.*;

public class ApplicationClass extends SugarApp {
    @Override
    public void onCreate() {
        // TODO: Implement this method
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
