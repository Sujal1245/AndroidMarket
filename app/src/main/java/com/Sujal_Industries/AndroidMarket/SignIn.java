package com.Sujal_Industries.AndroidMarket;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;

import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

import com.orm.query.*;

import java.util.*;

import androidx.appcompat.widget.Toolbar;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable dr = getResources().getDrawable(R.drawable.password);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        getSupportActionBar().setIcon(d);
        et1 = findViewById(R.id.signinEditText1);
        et2 = findViewById(R.id.signinEditText2);
        b = findViewById(R.id.signinButton1);
        accounts = Account.listAll(Account.class);
        ArrayList<String> names = new ArrayList<String>();
        long count = Account.count(Account.class);
        for (int x = 0; x < count; x++) {
            names.add(accounts.get(x).getName());
        }
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list, names);
        ListView accounts123 = findViewById(R.id.signinListView1);
        accounts123.setAdapter(aa);
        accounts123.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                // TODO: Implement this method
                Intent i = new Intent(getApplicationContext(), Market.class);
                Account account = accounts.get(p3);
                i.putExtra("User", account.getUser_name());
                i.putExtra("Name", account.getName());
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
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
            }
        });
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
