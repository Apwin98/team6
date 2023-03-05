package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.myapplication.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {


    private static final String KEY_CITY = "city";
    private static final String KEY_LOCATION_NAME = "location_name";
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTextCity;
    private EditText editLocationName;
    private FragmentSecondBinding binding;
    String city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        getParentFragmentManager().setFragmentResultListener("string1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                city= result.getString("f1");
                //System.out.println("chosen city: "+city);
            }
        });
//        System.out.println("Chosen city: "+city);
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        binding.FoodAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewLocations.class);
                intent.putExtra("city",city);
                intent.putExtra("category","Food");
                view.getContext().startActivity(intent);
            }
        });
        binding.culturalAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewLocations.class);
                intent.putExtra("city",city);
                intent.putExtra("category","Cultural");
                view.getContext().startActivity(intent);
            }
        });
        binding.NaturalAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewLocations.class);
                intent.putExtra("city",city);
                intent.putExtra("category","Natural");
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}