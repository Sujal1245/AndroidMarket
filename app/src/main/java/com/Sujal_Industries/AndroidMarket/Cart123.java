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

import java.util.*;

import androidx.appcompat.widget.Toolbar;

public class Cart123 extends AppCompatActivity {
    Button buy;
    Button orders;
    List<Cart> c_items;
    String user;
    TextView tv;
    ListView lv;
    Toolbar toolbar;
    StringBuilder details = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable dr = getResources().getDrawable(R.drawable.cart);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        getSupportActionBar().setIcon(d);
        tv = findViewById(R.id.cartTextView1);
        Data.total = 0;
        Intent i = getIntent();
        user = i.getStringExtra("User");
        c_items = Cart.find(Cart.class, "user = ?", user);
        ArrayList<String> al = new ArrayList<String>();
        for (int x = 0; x < c_items.size(); x++) {
            al.add(Items.findById(Items.class, c_items.get(x).getItem()).getName());
        }
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list, al);
        lv = findViewById(R.id.cartListView1);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                // TODO: Implement this method
                Intent i = new Intent(getApplicationContext(), ItemData.class);
                List<Cart> c_items123 = Cart.find(Cart.class, "user = ?", user);
                Cart c = c_items123.get(p3);
                i.putExtra("Cart", c);
                i.putExtra("User", user);
                i.putExtra("CartId", c.getId());
                i.putExtra("Flag", false);
                i.putExtra("Signal", 2);
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        buy = findViewById(R.id.cartButton1);
        for (int x = 0; x < c_items.size(); x++) {
            Data.total += c_items.get(x).getItemOb().getPrice() * c_items.get(x).getNumber();
        }
        buy.setText("Buy all for Rs." + Data.total);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                Intent i7899 = new Intent(getApplicationContext(), Animation.class);
                List<Account> accounts = Account.find(Account.class, "user = ?", user);
                Account c_acc = accounts.get(0);
                if (c_acc.getBalance() >= Data.total) {
                    if (checkStock()) {
                        List<Cart> c_items1245 = Cart.find(Cart.class, "user = ?", user);
                        for (int x = 0; x < c_items1245.size(); x++) {
                            details.append(c_items1245.get(x).getItemOb().getName() + "-" + c_items1245.get(x).getNumber() + "\n");
                            Items item = c_items1245.get(x).getItemOb();
                            item.setStock(item.getStock() - c_items1245.get(x).getNumber());
                            item.save();
                            if (item.getStock() == 0) {
                                item.delete();
                            }
                        }
                        Cart.deleteAll(Cart.class, "user = ?", user);
                        c_acc.setBalance(c_acc.getBalance() - Data.total);
                        c_acc.save();
                        Calendar c = Calendar.getInstance();
                        Date d = c.getTime();
                        String date = d.toLocaleString();
                        int price = Data.total;
                        Orders order = new Orders(user, details.toString(), date, price);
                        order.save();
                        i7899.putExtra("Msg", "Order Successfull!");
                        startActivity(i7899);
                        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    } else {
                        Toast.makeText(getApplicationContext(), "Insufficient Stock!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Insufficient Balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        orders = findViewById(R.id.cartButton2);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                Intent i123 = new Intent(getApplicationContext(), OrdersList.class);
                i123.putExtra("User", user);
                startActivity(i123);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        update(user);
    }

    public void update(String user) {
        c_items = Cart.find(Cart.class, "user = ?", user);
        ArrayList<String> al = new ArrayList<String>();
        for (int x = 0; x < c_items.size(); x++) {
            al.add(Items.findById(Items.class, c_items.get(x).getItem()).getName());
        }
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list, al);
        ListView lv = findViewById(R.id.cartListView1);
        lv.setAdapter(aa);
        if (c_items.size() == 0) {
            buy.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        } else {
            buy.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            Data.total = 0;
            for (int x = 0; x < c_items.size(); x++) {
                Data.total += c_items.get(x).getItemOb().getPrice() * c_items.get(x).getNumber();
            }
            buy.setText("Buy all for Rs." + Data.total);
        }
    }

    public boolean checkStock() {
        List<Cart> c_items123 = Cart.find(Cart.class, "user = ?", user);
        boolean check = true;
        for (int x = 0; x < c_items123.size(); x++) {
            if (c_items123.get(x).getItemOb().getStock() >= c_items123.get(x).getNumber()) {
            } else {
                check = false;
            }
        }
        return check;
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

    @Override
    protected void onResume() {
        // TODO: Implement this method
        super.onResume();
        update(user);
    }
}

class Data {
    static int total = 0;
}
