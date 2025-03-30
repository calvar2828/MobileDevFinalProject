package com.example.finalproject1.network;
//import retrofit classes
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //API url
    private static final String BASE_URL = "https://www.omdbapi.com/";

    private static Retrofit retrofit = null;

    //Access to retofit client
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Convertir JSON a objetos Java
                    .build();
        }
        return retrofit;
    }
}