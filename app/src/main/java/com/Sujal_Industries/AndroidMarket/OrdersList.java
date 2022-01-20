package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class OrdersList extends AppCompatActivity {
    private List<Orders> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list);
        Intent i = getIntent();
        String user = i.getStringExtra("User");
        ListView orders_list = findViewById(R.id.orderslistListView1);
        orders = Orders.find(Orders.class, "user = ?", user);
        ArrayList<String> data = new ArrayList<>();
        for (int x = 0; x < orders.size(); x++) {
            String date = orders.get(x).getDate();
            int price = orders.get(x).getPrice();
            data.add(date + "- Rs." + price);
        }
        ArrayAdapter<String> aa = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);
        orders_list.setAdapter(aa);
        orders_list.setOnItemClickListener((p1, p2, p3, p4) -> {
            // TODO: Implement this method
            Log.d("Alert7899", "Button Clicked!" + orders.get(p3).getId());
            Intent i123 = new Intent(getApplicationContext(), OrderData.class);
            i123.putExtra("Id", orders.get(p3).getId());
            startActivity(i123);
        });
    }
}
