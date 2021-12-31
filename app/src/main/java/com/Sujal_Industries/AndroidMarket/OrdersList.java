package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class OrdersList extends AppCompatActivity {
    private Intent i;
    private String user;
    private ListView orders_list;
    private List<Orders> orders;
    private ArrayList<String> data;
    private ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list);
        i = getIntent();
        user = i.getStringExtra("User");
        orders_list = findViewById(R.id.orderslistListView1);
        orders = Orders.find(Orders.class, "user = ?", user);
        data = new ArrayList<String>();
        for (int x = 0; x < orders.size(); x++) {
            String date = orders.get(x).getDate();
            int price = orders.get(x).getPrice();
            data.add(date + "- Rs." + price);
        }
        aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, data);
        orders_list.setAdapter(aa);
        orders_list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                // TODO: Implement this method
                Log.d("Alert7899", "Button Clicked!" + orders.get(p3).getId());
                Intent i123 = new Intent(getApplicationContext(), OrderData.class);
                i123.putExtra("Id", orders.get(p3).getId());
                startActivity(i123);
            }
        });
    }
}
