package com.example.finalproject1.models;

import java.io.Serializable;
import java.util.List;

public class MovieResponse implements Serializable {

    private List<Movie> Search;
    private String totalResults;
    private String Response;

    public List<Movie> getSearch() {
        return Search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return Response;
    }
}