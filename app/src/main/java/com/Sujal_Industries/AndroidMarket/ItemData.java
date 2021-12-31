package com.Sujal_Industries.AndroidMarket;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import android.view.*;
import android.widget.*;

import com.orm.query.*;

import java.util.*;

import android.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

public class ItemData extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4;
    Button b1, b2;
    String user;
    Intent i;
    String name;
    int price, stock, q;
    int signal;
    EditText quantity;
    EditText new_quantity;
    Toolbar toolbar;
    Items item;
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_data);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable dr = getResources().getDrawable(R.drawable.data);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        getSupportActionBar().setIcon(d);
        quantity = new EditText(this);
        quantity.setBackgroundColor(Color.LTGRAY);
        new_quantity = new EditText(this);
        new_quantity.setBackgroundColor(Color.LTGRAY);
        tv1 = findViewById(R.id.itemdataTextView1);
        tv2 = findViewById(R.id.itemdataTextView2);
        tv3 = findViewById(R.id.itemdataTextView3);
        tv4 = findViewById(R.id.itemdataTextView4);
        i = getIntent();
        signal = i.getIntExtra("Signal", 1);
        user = i.getStringExtra("User");
        item = (Items) i.getSerializableExtra("Item");
        cart = (Cart) i.getSerializableExtra("Cart");
        if (signal == 1) {
            name = item.getName();
            price = item.getPrice();
            stock = item.getStock();
        } else {
            name = cart.getItemOb().getName();
            price = cart.getItemOb().getPrice();
            stock = cart.getItemOb().getStock();
            q = cart.getNumber();
        }
        tv1.setText(name);
        tv2.setText("Rs." + price);
        tv3.setText("Stock Available:" + stock);
        b1 = findViewById(R.id.itemdataButton1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemData.this, R.style.MyDialogTheme);
                builder.setTitle("Quantity Required!");
                builder.setMessage("Please enter the quantity:");
                builder.setCancelable(false);
                builder.setView(quantity);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface p1, int p2) {
                        // TODO: Implement this method
                        int q = Integer.parseInt(quantity.getText().toString());
                        long id = i.getLongExtra("Id", 1);
                        Cart c = new Cart(id, user, q);
                        c.save();
                        finish();
                        Toast.makeText(getApplicationContext(), "Item Successfully Added To Cart!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface p1, int p2) {
                        // TODO: Implement this method
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        b2 = findViewById(R.id.itemdataButton2);
        boolean flag = i.getBooleanExtra("Flag", false);
        if (flag) {
            b1.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.GONE);
        } else {
            b1.setVisibility(View.GONE);
            tv4.setVisibility(View.VISIBLE);
            tv4.setText("Quantity:" + q);
        }
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                int signal = i.getIntExtra("Signal", 0);
                if (signal == 1) {
                    Items item123 = Items.findById(Items.class, i.getLongExtra("Id", 0));
                    item123.delete();
                    List<Cart> cart7899 = Select.from(Cart.class).where(Condition.prop("item").eq(i.getLongExtra("Id", 0))).list();
                    if (cart7899.size() != 0) {
                        Cart cart123 = cart7899.get(0);
                        cart123.delete();
                    }
                    finish();
                    Toast.makeText(getApplicationContext(), "Item Successfully removed!", Toast.LENGTH_SHORT).show();
                } else {
                    Cart c = Cart.findById(Cart.class, i.getLongExtra("CartId", 0));
                    c.delete();
                    finish();
                    Toast.makeText(getApplicationContext(), "Item Successfully removed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View p1) {
                // TODO: Implement this method
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemData.this, R.style.MyDialogTheme);
                builder.setTitle("Update Quantity!");
                builder.setMessage("Enter the new quantity:");
                builder.setCancelable(false);
                builder.setView(new_quantity);
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface p1, int p2) {
                        // TODO: Implement this method
                        int num = Integer.parseInt(new_quantity.getText().toString());
                        Cart c = Cart.findById(Cart.class, i.getLongExtra("CartId", 0));
                        c.setNumber(num);
                        c.save();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface p1, int p2) {
                        // TODO: Implement this method
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
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

    @Override
    protected void onResume() {
        // TODO: Implement this method
        super.onResume();
        tv4.setText("Quantity:" + q);
    }
}
