package com.Sujal_Industries.AndroidMarket;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;

import com.google.firebase.database.*;
import com.orm.query.*;

import java.util.*;

public class HelperService extends IntentService {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    DatabaseReference uRef;

    public HelperService() {
        super("HelperService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO: Implement this method
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent p1) {
        // TODO: Implement this method
        String user = p1.getStringExtra("User");
        int size = p1.getIntExtra("Size", 0);
        boolean done = p1.getBooleanExtra("Done", true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        if (isOnline()) {
            if (done) {
                for (int x = 0; x < size; x++) {
                    Items item = (Items) p1.getSerializableExtra("Data" + x);
                    uRef = userRef.child(user).child(item.getName());
                    uRef.setValue(item);
                }
            } else {
                List<Account> accs = Select.from(Account.class).where(Condition.prop("user").eq(user)).list();
                Account acc = accs.get(0);
                uRef = userRef.child(user);
                uRef.setValue(acc);
            }
        } else {
            onHandleIntent(p1);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
