package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Passbook extends AppCompatActivity {
    TextView tv1, tv2;
    String name;
    String user;
    String balance;
    Button add;
    List<Account> accounts;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passbook);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable dr = getResources().getDrawable(R.drawable.cashbook);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        getSupportActionBar().setIcon(d);
        Intent i = getIntent();
        tv1 = findViewById(R.id.passbookTextView1);
        tv2 = findViewById(R.id.passbookTextView2);
        user = i.getStringExtra("User");
        List<Account> accounts = Account.find(Account.class, "user = ?", user);
        Account account = accounts.get(0);
        balance = Integer.toString(account.getBalance());
        name = account.getName();
        tv1.setText(name);
        tv2.setText(balance);
        add = findViewById(R.id.passbookButton1);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                Intent i = new Intent(getApplicationContext(), AddMoney.class);
                i.putExtra("User", user);
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO: Implement this method
        super.onResume();
        accounts = Account.find(Account.class, "user = ?", user);
        Account account = accounts.get(0);
        balance = Integer.toString(account.getBalance());
        name = account.getName();
        tv1.setText(name);
        tv2.setText(balance);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Implement this method
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
