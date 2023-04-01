package com.example.myapplication;

import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.net.URI;
import java.net.URL;

public class Locations implements Parcelable {
    private String Location, Description, Address, City, Category, Key, Url;

    public Locations(){}

    public Locations(String Location, String Description, String Address, String City, String Category,String Key, String Url){

        this.Location = Location;
        this.Description = Description;
        this.Address = Address;
        this.City = City;
        this.Category = Category;
        this.Key = Key;
        this.Url = Url;
    }

    protected Locations(Parcel in) {
        Location = in.readString();
        Description = in.readString();
        Address = in.readString();
        City = in.readString();
        Category = in.readString();
        Key = in.readString();
        Url = in.readString();
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
    public String getUrl(){
        return Url;
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
    public void setUrl(String name){
        Url=name;
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
        dest.writeString(Url);
    }
}
