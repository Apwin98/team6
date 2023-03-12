package com.example.myapplication;

import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.net.URL;

public class Locations implements Parcelable {
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

    protected Locations(Parcel in) {
        Location = in.readString();
        Description = in.readString();
        Address = in.readString();
        City = in.readString();
        Category = in.readString();
        Key = in.readString();
    }

    public static final Creator<Locations> CREATOR = new Creator<Locations>() {
        @Override
        public Locations createFromParcel(Parcel in) {
            return new Locations(in);
        }

        @Override
        public Locations[] newArray(int size) {
            return new Locations[size];
        }
    };

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
    public void setLocation(String name){Location=name;}
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(Location);
        dest.writeString(Description);
        dest.writeString(Address);
        dest.writeString(City);
        dest.writeString(Category);
        dest.writeString(Key);
    }
}
