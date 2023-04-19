package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
//import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ViewLocations extends AppCompatActivity implements RecyclerViewInterface{

//    TextView textView1;
//    TextView textView2;
    ArrayList<Locations> locationsArray = new ArrayList<>();
    SearchView searchView;
    L_RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_locations);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#112D2B"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //textView1=findViewById(R.id.textView);
        //textView2=findViewById(R.id.textView2);

        Intent intent=getIntent();
        String city=intent.getStringExtra("city");
        String category=intent.getStringExtra("category");
        //textView1.setText(city);
        //textView2.setText(category);

        //Setting up search view
        searchView=findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        //setting up recycler view
        RecyclerView recyclerView=findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new L_RecyclerViewAdapter(this,locationsArray, this);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Locations");
        //if for some reason Category is now lowercase, just change "Category" to "category"
        ref.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Locations location = postSnapshot.getValue(Locations.class);
                    if(location.getCity().equals(city)) {
                        locationsArray.add(location);
                    }
                }
                for(int i=0;i<locationsArray.size();i++) {
                    //show the names that are queried
                    System.out.println("****"+ locationsArray.get(i).getLocation() + "****");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void filterList(String text) {
        ArrayList<Locations> filteredList = new ArrayList<>();
        for(Locations location:locationsArray)
        {
            if(location.getLocation().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(location);
            }
        }
        if(filteredList.isEmpty())
        {
            //Toast.makeText(this, "No locations found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            adapter.setFilteredList(filteredList);
        }

    }

    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //emptying the array (just in case)
                locationsArray.clear();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent= new Intent(ViewLocations.this, LocationProfile.class);
        intent.putExtra("location",locationsArray.get(position));
        startActivity(intent);
    }
}