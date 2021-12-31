package com.Sujal_Industries.AndroidMarket;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private static final int STATIC_INTEGER_VALUE = 420;
    Button b1, b2, b3;
    Toolbar toolbar;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable dr = getResources().getDrawable(R.drawable.android1);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        getSupportActionBar().setIcon(d);

        b1 = findViewById(R.id.mainButton1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                Intent i = new Intent(getApplicationContext(), SignIn.class);
                startActivity(i);
                overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);
            }
        });
        b2 = findViewById(R.id.mainButton2);
        b2.setOnClickListener(p1 -> {
            // TODO: Implement this method
            Intent i = new Intent(getApplicationContext(), SignUp.class);
            startActivityForResult(i, STATIC_INTEGER_VALUE);
            overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
        });
        b3 = findViewById(R.id.mainButton3);
        b3.setOnClickListener(p1 -> {
            // TODO: Implement this method
            Toast.makeText(getApplicationContext(),"Sorry but this section is under construction!",Toast.LENGTH_SHORT).show();
        });
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        Intent i = getIntent();
        boolean created = i.getBooleanExtra("Account", false);
        if (created) {
            Snackbar snak = Snackbar.make(findViewById(R.id.mainRelativeLayout1), "Account Successfully Created!", Snackbar.LENGTH_SHORT);
            snak.show();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO: Implement this method
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case STATIC_INTEGER_VALUE: {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    Snackbar snak = Snackbar.make(findViewById(R.id.mainRelativeLayout1), "Account Successfully Created!", Snackbar.LENGTH_SHORT);
                    snak.show();
                }
            }
            break;
        }
    }

}
