package com.Sujal_Industries.AndroidMarket;

import android.animation.*;
import android.content.*;
import android.os.*;
import androidx.appcompat.widget.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

import java.util.*;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.*;
import androidx.core.app.*;

import com.oguzdev.circularfloatingactionmenu.library.*;

public class Market extends DrawerActivity {
    List<Items> items;
    String user;
    TextView name;
    TextView tv;
    ListView lv;
    Toolbar toolbar;

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
        ArrayList<String> al = new ArrayList<String>();
        for (int x = 0; x < count; x++) {
            al.add(items.get(x).getName());
        }
        ArrayAdapter aa = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_list, al);
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
        icon.setImageDrawable(getResources().getDrawable(R.drawable.add));

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

        itemIcon1.setImageDrawable(getResources().getDrawable(R.drawable.add));
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.cart));
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.cashbook));
        itemIcon4.setImageDrawable(getResources().getDrawable(R.drawable.data));
        itemIcon5.setImageDrawable(getResources().getDrawable(R.drawable.android1));

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
        itemIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                Intent i = new Intent(getApplicationContext(), AddItem.class);
                i.putExtra("UserName", user);
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        itemIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                Intent i = new Intent(getApplicationContext(), Cart123.class);
                i.putExtra("User", user);
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        itemIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                Intent i = new Intent(getApplicationContext(), Passbook.class);
                i.putExtra("User", user);
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        itemIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                Intent i = new Intent(getApplicationContext(), Settings.class);
                i.putExtra("UserName", user);
                startActivity(i);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });
        itemIcon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                finish();
            }
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
        ArrayList<String> al = new ArrayList<String>();
        for (int x = 0; x < count; x++) {
            al.add(items.get(x).getName());
        }
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list, al);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
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
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
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
