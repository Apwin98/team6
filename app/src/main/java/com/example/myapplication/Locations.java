package com.example.myapplication;

import android.net.Uri;
import android.net.UrlQuerySanitizer;

import java.net.URL;

public class Locations {
    public String Location, Description, Address, City, Category, Key;


    public Locations(){}

    public Locations(String Location, String Description, String Address, String City, String Category,String Key){

        this.Location = Location;
        this.Description = Description;
        this.Address = Address;
        this.City = City;
        this.Category = Category;
        this.Key = Key;

    }

}
