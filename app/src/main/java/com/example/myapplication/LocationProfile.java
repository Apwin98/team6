package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class LocationProfile extends AppCompatActivity {

    TextView tvLocation,tvDescription,tvAddress,tvCity,tvCategory,tvKey;
    ImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvLocation=findViewById(R.id.textView);
        tvDescription=findViewById(R.id.textView2);
        tvAddress=findViewById(R.id.textView3);
        tvCity=findViewById(R.id.textView4);
        tvCategory=findViewById(R.id.textView5);
        ivImage = findViewById(R.id.imageView);
        //tvKey=findViewById(R.id.textView6);

        Locations location=getIntent().getParcelableExtra("location");
        tvLocation.setText(location.getLocation());
        tvDescription.setText(location.getDescription());
        tvAddress.setText(location.getAddress());
        tvCity.setText(location.getCity());
        tvCategory.setText(location.getCategory());


        Picasso.get().load(location.getUrl()).into(ivImage);

       // ivImage.setImageURI();
        //tvKey.setText(location.getKey());
    }


    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}