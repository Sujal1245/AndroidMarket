package com.Sujal_Industries.AndroidMarket;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

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
            e.printStackTrace();
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
