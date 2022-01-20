package com.Sujal_Industries.AndroidMarket;

import android.os.*;
import androidx.appcompat.app.*;
import android.util.*;
import android.widget.*;

public class Animation extends AppCompatActivity {
    ImageView img;
    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation);
        img = findViewById(R.id.animationImageView1);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        Handler handle = new Handler();
        handle.postDelayed(() -> {
            // TODO: Implement this method
            img.animate().rotation(360f).translationY(-getResources().getDimension(R.dimen.anim_400)).translationX(getResources().getDimension(R.dimen.anim_100)).setDuration(2000);
            Handler handle2 = new Handler();
            // TODO: Implement this method
            handle2.postDelayed(this::finish, 2100);
        }, 1500);
    }
}
