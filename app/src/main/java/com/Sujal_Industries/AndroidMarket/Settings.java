package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    Button b1;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Intent i = getIntent();
        user = i.getStringExtra("UserName");
        b1 = findViewById(R.id.settingsButton1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                List<Account> accounts = Account.find(Account.class, "user = ?", user);
                Account account = accounts.get(0);
                List<Items> items = Items.find(Items.class, "user = ?", user);
                List<Cart> cart = Cart.find(Cart.class, "user = ?", user);
                account.delete();
                if (items != null) {
                    for (int x = 0; x < items.size(); x++) {
                        Items i123 = items.get(x);
                        i123.delete();
                    }
                }
                if (cart != null) {
                    for (int y = 0; y < cart.size(); y++) {
                        Cart c123 = cart.get(y);
                        c123.delete();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
