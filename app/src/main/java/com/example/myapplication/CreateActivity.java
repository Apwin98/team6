package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.EventLogTags;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityCreateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {
    ActivityCreateBinding binding;
    FirebaseDatabase db;
    DatabaseReference reference;
     Spinner City, Category;
    String categoryItem, cityItem, Locationname, Description, Address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Hidden Gem");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Category DropDown
        Spinner Category = findViewById(R.id.categoryDropDwn);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Food");
        categories.add("Cultural");
        categories.add("Natural");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        Category.setAdapter(arrayAdapter);

        //records the selected category
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                categoryItem = categories.get(position);
                //Toast.makeText(CreateActivity.this, categoryItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //City Dropdown
        Spinner City = findViewById(R.id.cityDropDwn);
        ArrayList<String> cities = new ArrayList<>();
        cities.add("Arlington");
        cities.add("Fort Worth");
        cities.add("Dallas");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        City.setAdapter(arrayAdapter2);
        //records the selected city
        City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                cityItem = cities.get(position);
                //Toast.makeText(CreateActivity.this, cityItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.submitAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locationname = binding.nameInput.getText().toString();
                Description = binding.decriptionInput.getText().toString();
                Address = binding.streetInput.getText().toString();
                if(!Locationname.isEmpty() && !Description.isEmpty() && !Address.isEmpty() && !cityItem.isEmpty() && !categoryItem.isEmpty()){

                    Locations locations = new Locations(Locationname, Description, Address, cityItem, categoryItem);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Locations");
                    reference.child(Locationname).setValue(locations).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.nameInput.setText("");
                            binding.decriptionInput.setText("");
                            binding.streetInput.setText("");
                            //Toast.makeText(CreateActivity.this, "Successful something!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    //back button
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}