package com.example.finalproject1.utils;

import android.content.Context;

import com.example.finalproject1.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {

    private static final String FILE_NAME = "favorites.json";

    public static void saveMovie(Context context, Movie movie) {
        List<Movie> favorites = getFavorites(context);
        favorites.add(movie);
        saveFavoritesToFile(context, favorites);
    }

    public static List<Movie> getFavorites(Context context) {
        List<Movie> favorites = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();
            String json = new String(data, "UTF-8");
            Type listType = new TypeToken<List<Movie>>() {}.getType();
            favorites = new Gson().fromJson(json, listType);
            if (favorites == null) favorites = new ArrayList<>();
        } catch (Exception e) {
            // File doesn't exist yet, no problem
        }
        return favorites;
    }

    private static void saveFavoritesToFile(Context context, List<Movie> favorites) {
        try {
            String json = new Gson().toJson(favorites);
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
