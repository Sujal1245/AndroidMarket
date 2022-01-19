package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderData extends AppCompatActivity {
    TextView tv;
    Intent i;
    long id123;
    Orders order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_data);
        i = getIntent();
        id123 = i.getLongExtra("Id", 1);
        order = Orders.findById(Orders.class, id123);
        tv = findViewById(R.id.orderdataTextView1);
        tv.setText(order.getDetails());
    }
}
