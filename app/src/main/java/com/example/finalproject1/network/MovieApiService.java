package com.example.finalproject1.network;

import com.example.finalproject1.models.Movie;
import com.example.finalproject1.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("/")
    Call<Movie> getMovie(@Query("t") String title, @Query("apikey") String apiKey);



}
