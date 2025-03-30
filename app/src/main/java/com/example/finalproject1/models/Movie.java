package com.example.finalproject1.models;

import java.io.Serializable;


//serializable is used to pack an object in a format that android will use it to send the info between pages
public class Movie implements Serializable {

    //this names have to match with the OMDb API response(case sensitive)
    private String Title;
    private String Year;
    private String Director;
    private String Actors;
    private String imdbRating;
    private String Plot;
    private String Poster;

    public String getPoster() {
        return Poster;
    }

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getDirector() {
        return Director;
    }

    public String getActors() {
        return Actors;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getPlot() {
        return Plot;
    }
}
