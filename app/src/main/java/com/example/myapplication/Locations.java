package com.example.myapplication;

import android.net.Uri;
import android.net.UrlQuerySanitizer;

import java.net.URL;

public class Locations {
    private String Location, Description, Address, City, Category, Key;


    public Locations(){}

    public Locations(String Location, String Description, String Address, String City, String Category,String Key){

        this.Location = Location;
        this.Description = Description;
        this.Address = Address;
        this.City = City;
        this.Category = Category;
        this.Key = Key;

    }
    public String getLocation(){
        return Location;
    }
    public String getDescription(){
        return Description;
    }
    public String getAddress(){
        return Address;
    }
    public String getCity(){
        return City;
    }
    public String getCategory(){
        return Category;
    }
    public String getKey(){
        return Key;
    }

    //testing
    public void setLocation(String name){
        Location=name;
    }
    public void setDescription(String name){
        Description=name;
    }
    public void setAddress(String name){
        Address=name;
    }
    public void setCity(String name){
        City=name;
    }
    public void setCategory(String name){
        Category=name;
    }
    public void setKey(String name){
        Key=name;
    }

}
