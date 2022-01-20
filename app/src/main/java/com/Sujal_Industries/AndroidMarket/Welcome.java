package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        tv = findViewById(R.id.welcomeTextView1);
        tv.setTextColor(Color.rgb(34, 139, 34));
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(i);
        }, 3000);
    }
}