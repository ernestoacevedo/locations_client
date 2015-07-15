package com.example.ernesto.location.models;

/**
 * Created by ernesto on 13-07-15.
 */
public class LocationModel {
    private String name;
    private String description;
    private String lat;
    private String lng;

    public String getName(){
        return name;
    }

    public void setName(String n){
        this.name = n;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String n){
        this.description = n;
    }

    public String getLat(){
        return lat;
    }

    public void setLat(String n){
        this.lat = n;
    }

    public String getLng(){
        return lng;
    }

    public void setLng(String n){
        this.lng = n;
    }


}




