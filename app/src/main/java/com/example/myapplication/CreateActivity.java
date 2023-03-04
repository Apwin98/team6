package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Hidden Gem");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Category DropDown
        Spinner spinner = findViewById(R.id.categoryDropDwn);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Food");
        categories.add("Cultural");
        categories.add("Natural");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(arrayAdapter);

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