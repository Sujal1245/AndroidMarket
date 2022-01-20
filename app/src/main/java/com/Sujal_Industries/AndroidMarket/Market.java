package com.Sujal_Industries.AndroidMarket;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

public class Market extends DrawerActivity {
    List<Items> items;
    String user;
    TextView name;
    TextView tv;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.market, null, false);
        drawerLayout.addView(contentView, 0);
        Intent i = getIntent();
        name = findViewById(R.id.Name);
        name.setText(i.getStringExtra("Name"));
        user = i.getStringExtra("User");
        super.setUserName(user);
        super.showName(i.getStringExtra("Name"));
        super.showUser(user);
        items = Items.find(Items.class, "user = ?", user);
        int count = items.size();
        tv = findViewById(R.id.marketTextView1);
        tv.setText(count + " items");
        lv = findViewById(R.id.marketListView1);
        ArrayList<String> al = new ArrayList<>();
        for (int x = 0; x < count; x++) {
            al.add(items.get(x).getName());
        }
        ArrayAdapter<String> aa = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_list, al);
        lv.setAdapter(aa);
        lv.setOnItemClickListener((p1, p2, p3, p4) -> {
            // TODO: Implement this method
            Intent i1 = new Intent(getApplicationContext(), ItemData.class);
            items = Items.find(Items.class, "user = ?", user);
            Items item = items.get(p3);
            long id = item.getId();
            i1.putExtra("Item", item);
            i1.putExtra("User", user);
            i1.putExtra("Id", id);
            i1.putExtra("Flag", true);
            i1.putExtra("Signal", 1);
            startActivity(i1);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
        //Floating Action Button Section
        final ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.add));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                .setBackgroundDrawable(R.drawable.mb)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(150, 150);
        itemBuilder.setLayoutParams(params);

        ImageView itemIcon1 = new ImageView(this);
        ImageView itemIcon2 = new ImageView(this);
        ImageView itemIcon3 = new ImageView(this);
        ImageView itemIcon4 = new ImageView(this);
        ImageView itemIcon5 = new ImageView(this);

        itemIcon1.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.add));
        itemIcon2.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cart));
        itemIcon3.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cashbook));
        itemIcon4.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.data));
        itemIcon5.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.android1));

        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();
        SubActionButton button5 = itemBuilder.setContentView(itemIcon5).build();
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(180)
                .setEndAngle(360)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)
                .addSubActionView(button5)
                .attachTo(actionButton)
                .build();
        actionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                icon.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(icon, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                icon.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(icon, pvhR);
                animation.start();
            }
        });
        itemIcon1.setOnClickListener(p1 -> {
            // TODO: Implement this method
            Intent i12 = new Intent(getApplicationContext(), AddItem.class);
            i12.putExtra("UserName", user);
            startActivity(i12);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
        itemIcon2.setOnClickListener(p1 -> {
            // TODO: Implement this method
            Intent i13 = new Intent(getApplicationContext(), Cart123.class);
            i13.putExtra("User", user);
            startActivity(i13);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
        itemIcon3.setOnClickListener(p1 -> {
            // TODO: Implement this method
            Intent i14 = new Intent(getApplicationContext(), Passbook.class);
            i14.putExtra("User", user);
            startActivity(i14);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
        itemIcon4.setOnClickListener(p1 -> {
            // TODO: Implement this method
            Intent i15 = new Intent(getApplicationContext(), Settings.class);
            i15.putExtra("UserName", user);
            startActivity(i15);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
        itemIcon5.setOnClickListener(p1 -> {
            // TODO: Implement this method
            finish();
        });
    }

    @Override
    protected void onResume() {
        // TODO: Implement this method
        super.onResume();
        items = Items.find(Items.class, "user = ?", user);
        int count = items.size();
        tv = findViewById(R.id.marketTextView1);
        tv.setText(count + " items");
        lv = findViewById(R.id.marketListView1);
        ArrayList<String> al = new ArrayList<>();
        for (int x = 0; x < count; x++) {
            al.add(items.get(x).getName());
        }
        ArrayAdapter<String> aa = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_list, al);
        lv.setAdapter(aa);
        lv.setOnItemClickListener((p1, p2, p3, p4) -> {
            // TODO: Implement this method
            Intent i = new Intent(getApplicationContext(), ItemData.class);
            items = Items.find(Items.class, "user = ?", user);
            Items item = items.get(p3);
            i.putExtra("Item", item);
            i.putExtra("User", user);
            i.putExtra("Id", item.getId());
            i.putExtra("Flag", true);
            i.putExtra("Signal", 1);
            startActivity(i);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home -> {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        // TODO: Implement this method
        super.onDestroy();
        Intent s_intent = new Intent(getApplicationContext(), HelperService.class);
        for (int x = 0; x < items.size(); x++) {
            s_intent.putExtra("Data" + x, items.get(x));
        }
        s_intent.putExtra("Size", items.size());
        s_intent.putExtra("User", user);
        startService(s_intent);
    }

}
