package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityCreateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {
    ActivityCreateBinding binding;
    FirebaseDatabase db;

    StorageReference storageReference;
    DatabaseReference reference;


    Spinner City, Category;
    String categoryItem, cityItem, Locationname, Description, Address, imageUrl;

    //Upload Images
    Uri imageUri;

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


        //Image Activity Launcher
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        TextView imageName = (TextView) findViewById(R.id.imageName);

                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            imageName.setText(getFileName(imageUri));

                        }else {
                            Toast.makeText(CreateActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
        //Upload Images
        Button uploadBtn = findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);

            }
        });

        binding.submitAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locationname = binding.nameInput.getText().toString();
                Description = binding.decriptionInput.getText().toString();
                Address = binding.streetInput.getText().toString();

                if(!Locationname.isEmpty() && !Description.isEmpty() && !Address.isEmpty() && !cityItem.isEmpty() && !categoryItem.isEmpty() && (imageUri != null)){

                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Locations");
                    String key = reference.push().getKey();

                    storageReference = FirebaseStorage.getInstance().getReference(Locationname).child(getFileName(imageUri));
                    //.getInstance().getReference("Locations");
                    storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                     uri.toString();
                                    //reference.child(key).setValue(uri.toString());
                                    //imageUrl = url_link;
                                    reference.child(Locationname).child("url").setValue(uri.toString());
                                }
                            });
                        }
                    });

                    Locations locations = new Locations(Locationname, Description, Address, cityItem, categoryItem, key, imageUrl);

                    reference.child(Locationname).setValue(locations).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.nameInput.setText("");
                            binding.decriptionInput.setText("");
                            binding.streetInput.setText("");



                            Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            //Toast.makeText(CreateActivity.this, "Successful something!", Toast.LENGTH_SHORT).show();
                        }

                    });

                }else{
                    Toast.makeText(CreateActivity.this, "Fill out all parts", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private String getFileExt(Uri fileUri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }

    //Get image file name
    @SuppressLint("Range")
    private String getFileName(Uri imageUri) {
//        String path = imageUri.toString();
//        int lastSlashIndex = path.lastIndexOf("/");
//        return path.substring(lastSlashIndex + 1, path.length());
        String result = null;
        if (imageUri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(imageUri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = imageUri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
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