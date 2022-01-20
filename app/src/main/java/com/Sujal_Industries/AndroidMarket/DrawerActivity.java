package com.Sujal_Industries.AndroidMarket;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DrawerActivity extends AppCompatActivity {
    String user;
    ImageView imgProfile;
    Toolbar myToolbar;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigation;
    View navHeader;
    TextView name123, userName;
    private static final int RESULT_LOAD_IMG = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference sRef;
    UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigation = findViewById(R.id.nav_view);
        navHeader = navigation.getHeaderView(0);
        imgProfile = navHeader.findViewById(R.id.img_profile);
        name123 = navHeader.findViewById(R.id.name);
        userName = navHeader.findViewById(R.id.user);
        setSupportActionBar(myToolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.open, R.string.close) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
                //drawerOpened = false;
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
                //drawerOpened = true;
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        navigation.setNavigationItemSelectedListener(p1 -> {
            // TODO: Implement this method
            p1.setChecked(true);
            drawerLayout.closeDrawers();
            int id = p1.getItemId();
            navigation.setCheckedItem(id);
            switch (id) {
                case R.id.nav_market:
                    break;
                case R.id.nav_cart:
                    Intent i1 = new Intent(getApplicationContext(), Cart123.class);
                    i1.putExtra("User", user);
                    startActivity(i1);
                    overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    break;
                case R.id.nav_passbook:
                    Intent i2 = new Intent(getApplicationContext(), Passbook.class);
                    i2.putExtra("User", user);
                    startActivity(i2);
                    overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    break;
                case R.id.nav_add:
                    Intent i3 = new Intent(getApplicationContext(), AddItem.class);
                    i3.putExtra("UserName", user);
                    startActivity(i3);
                    overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    break;
                case R.id.nav_settings:
                    Intent i4 = new Intent(getApplicationContext(), Settings.class);
                    i4.putExtra("UserName", user);
                    startActivity(i4);
                    overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    break;
            }
            return true;
        });
        Drawable dr = AppCompatResources.getDrawable(this, R.drawable.shop);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        getSupportActionBar().setIcon(d);
        navigation.setCheckedItem(R.id.nav_market);
        imgProfile.setOnClickListener(p1 -> {
            // TODO: Implement this method
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        });
        if (loadImageFromStorage("/sdcard/ImgProfile/") != null) {
            Glide.with(this).load(loadImageFromStorage("/sdcard/ImgProfile/")).apply(RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(imgProfile);
        } else {
            Glide.with(this).load(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android1)).apply(RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(imgProfile);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setUserName(String user) {
        this.user = user;
    }

    public void showName(String name) {
        name123.setText(name);
    }

    public void showUser(String user123) {
        userName.setText(user123);
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/ImgProfile");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/ImgProfile/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/ImgProfile/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File loadImageFromStorage(String path) {

        return new File(new File(path), "Profile.jpg");

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                createDirectoryAndSaveFile(selectedImage, "Profile.jpg");
                Glide.with(this).load(imageUri).apply(RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(imgProfile);
                Uri file = Uri.fromFile(loadImageFromStorage("/sdcard/ImgProfile/"));
                sRef = storage.getReference("images").child(user);
                uploadTask = sRef.putFile(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(DrawerActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(DrawerActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
