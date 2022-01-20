package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passbook);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Drawable dr = AppCompatResources.getDrawable(this, R.drawable.cashbook);
        Bitmap bitmap = null;
        if (dr != null) {
            bitmap = ((BitmapDrawable) dr).getBitmap();
        }
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
        add.setOnClickListener(p1 -> {
            // TODO: Implement this method
            Intent i1 = new Intent(getApplicationContext(), AddMoney.class);
            i1.putExtra("User", user);
            startActivity(i1);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
    }

    @Override
    protected void onResume() {
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
        switch (item.getItemId()) {
            case android.R.id.home -> {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
