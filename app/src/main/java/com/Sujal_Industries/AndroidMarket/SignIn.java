package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {
    EditText et1, et2;
    Button b;
    List<Account> accounts;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Drawable dr = AppCompatResources.getDrawable(this, R.drawable.password);
        Bitmap bitmap = null;
        if (dr != null) {
            bitmap = ((BitmapDrawable) dr).getBitmap();
        }
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        getSupportActionBar().setIcon(d);
        et1 = findViewById(R.id.signinEditText1);
        et2 = findViewById(R.id.signinEditText2);
        b = findViewById(R.id.signinButton1);
        accounts = Account.listAll(Account.class);
        ArrayList<String> names = new ArrayList<>();
        long count = Account.count(Account.class);
        for (int x = 0; x < count; x++) {
            names.add(accounts.get(x).getName());
        }
        ArrayAdapter<String> aa = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_list, names);
        ListView accounts123 = findViewById(R.id.signinListView1);
        accounts123.setAdapter(aa);
        accounts123.setOnItemClickListener((p1, p2, p3, p4) -> {
            // TODO: Implement this method
            Intent i = new Intent(getApplicationContext(), Market.class);
            Account account = accounts.get(p3);
            i.putExtra("User", account.getUser_name());
            i.putExtra("Name", account.getName());
            startActivity(i);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
        b.setOnClickListener(p1 -> {
            // TODO: Implement this method
            String user = et1.getText().toString();
            String pass = et2.getText().toString();
            List<Account> account = Select.from(Account.class).where("user = ?", new String[]{user}).list();
            if (user.equals("") || pass.equals("")) {
                Account acc = new Account("Guest", "Guest123", "0000", 1000);
                acc.save();
                Intent i = new Intent(getApplicationContext(), Market.class);
                i.putExtra("User", acc.getUser_name());
                i.putExtra("Name", acc.getName());
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                Toast.makeText(getApplicationContext(), "Sign In Complete!", Toast.LENGTH_SHORT).show();
            } else if (account.isEmpty()) {
                Toast.makeText(getApplicationContext(), "User Name does not exist!", Toast.LENGTH_SHORT).show();
            } else {
                Account acc = account.get(0);
                if (pass.equals(acc.getPassword())) {
                    Intent i = new Intent(getApplicationContext(), Market.class);
                    i.putExtra("User", user);
                    i.putExtra("Name", acc.getName());
                    startActivity(i);
                    overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    Toast.makeText(getApplicationContext(), "Sign In Complete!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Details!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Implement this method
        switch (item.getItemId()) {
            case android.R.id.home -> {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
