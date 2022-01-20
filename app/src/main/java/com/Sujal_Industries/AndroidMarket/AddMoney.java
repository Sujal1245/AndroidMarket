package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddMoney extends AppCompatActivity {
    String user;
    Button add;
    EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money);
        Intent i = getIntent();
        user = i.getStringExtra("User");
        amount = findViewById(R.id.addmoneyEditText1);
        add = findViewById(R.id.addmoneyButton1);
        add.setOnClickListener(p1 -> {
            // TODO: Implement this method
            try {
                int amount_to_add = Integer.parseInt(amount.getText().toString());
                List<Account> accounts = Account.find(Account.class, "user = ?", user);
                Account c_account = accounts.get(0);
                c_account.setBalance(c_account.getBalance() + amount_to_add);
                c_account.save();
                Toast.makeText(getApplicationContext(), "Money Successfully Added!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (NumberFormatException nfe) {
                Toast.makeText(getApplicationContext(), "Please enter a valid integer!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
