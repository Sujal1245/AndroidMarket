package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

public class AddItem extends AppCompatActivity {
    EditText et1, et2, et3;
    Button b;
    String user;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Drawable dr = AppCompatResources.getDrawable(this, R.drawable.item);
        Bitmap bitmap = null;
        if (dr != null) {
            bitmap = ((BitmapDrawable) dr).getBitmap();
        }
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        getSupportActionBar().setIcon(d);
        Intent i = getIntent();
        user = i.getStringExtra("UserName");
        b = findViewById(R.id.additemButton1);
        et1 = findViewById(R.id.additemEditText1);
        et2 = findViewById(R.id.additemEditText2);
        et3 = findViewById(R.id.additemEditText3);
        b.setOnClickListener(p1 -> {
            // TODO: Implement this method
            try {
                String name = et1.getText().toString();
                int price = Integer.parseInt(et2.getText().toString());
                int stock = Integer.parseInt(et3.getText().toString());
                Items item = new Items(name, price, stock, user);
                item.save();
                Toast.makeText(getApplicationContext(), "Item Successfully Added!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (NumberFormatException nfe) {
                Toast.makeText(getApplicationContext(), "Please enter valid details!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home -> {
                System.out.println("Back Button Pressed!");
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
