package com.Sujal_Industries.AndroidMarket;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import androidx.core.app.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import android.view.*;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.*;

import androidx.core.app.NotificationCompat;
import androidx.appcompat.widget.Toolbar;

public class SignUp extends AppCompatActivity {
    Button create;
    EditText name, user, pass;
    Toolbar toolbar;
    String CHANNEL_ID = "channel_01";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    DatabaseReference uRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        create = findViewById(R.id.signupButton1);
        name = findViewById(R.id.signupName);
        user = findViewById(R.id.signupUserName);
        pass = findViewById(R.id.signupPassword);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                String name1 = name.getText().toString();
                String user1 = user.getText().toString();
                String pass1 = pass.getText().toString();
                List<Account> accounts = Account.find(Account.class, "user = ?", user1);
                if (accounts.size() == 0) {
                    Account account = new Account(name1, user1, pass1, 1000);
                    account.save();
                    if (isOnline()) {
                        uRef = userRef.child(user1);
                        uRef.setValue(account);
                    } else {
                        Intent i = new Intent(getApplicationContext(), HelperService.class);
                        i.putExtra("User", user1);
                        i.putExtra("Done", false);
                        startService(i);
                    }
                    createNotification("Bonus Recieved!", name1);
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Account with this user name already exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createNotification(String title, String name) {
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "In-app notifications", importance);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder noti = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText("---FOR " + name + ".---")
                    .setSmallIcon(R.drawable.android1)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Congratulations! You have got Rs.1000 as a bonus for login.Click on this notification to login now!!!"))
                    .setContentIntent(pIntent)
                    .setPriority(NotificationManager.IMPORTANCE_MAX);
            notificationManager.notify(0, noti.build());
        } else {
            NotificationCompat.Builder noti = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText("---FOR " + name + ".---")
                    .setSmallIcon(R.drawable.android1)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Congratulations! You have got Rs.1000 as a bonus for login.Click on this notification to login now!!!"))
                    .setContentIntent(pIntent);
            notificationManager.notify(0, noti.build());
        }
        // hide the notification after its se	noti.flags |= Notification.FLAG_AUTO_CANCEL;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.out.println("Back Button Pressed!");
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
