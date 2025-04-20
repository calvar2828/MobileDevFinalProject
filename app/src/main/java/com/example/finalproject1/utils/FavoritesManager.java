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

// this class manages saving and loading favorite movies using a local JSON file
public class FavoritesManager {

    //file name where favorites are strored
    private static final String FILE_NAME = "favorites.json";

    //save a new movie to the favorites list
    public static void saveMovie(Context context, Movie movie) {
        List<Movie> favorites = getFavorites(context);
        favorites.add(movie);
        saveFavoritesToFile(context, favorites);
    }
    // load the list of favorite movies from local storage
    public static List<Movie> getFavorites(Context context) {
        List<Movie> favorites = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);//open the file
            byte[] data = new byte[fis.available()];
            fis.read(data);//read the data
            fis.close();
            String json = new String(data, "UTF-8");//convert to json  string
            Type listType = new TypeToken<List<Movie>>() {}.getType();
            favorites = new Gson().fromJson(json, listType);//deserialize JSON to List<Movie>
            if (favorites == null) favorites = new ArrayList<>();
        } catch (Exception e) {
            // File doesn't exist yet, no problem
        }
        return favorites;//return the list
    }

    //save the full list of favorite movie to the file
    private static void saveFavoritesToFile(Context context, List<Movie> favorites) {
        try {
            String json = new Gson().toJson(favorites);
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(json.getBytes());//write jsin to file
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //remove a movie from the favorites list
    public static void removeFromFavorites(Context context, Movie movie) {
        List<Movie> favorites = getFavorites(context);//load
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getTitle().equalsIgnoreCase(movie.getTitle())) {
                favorites.remove(i);
                break;
            }
        }
        saveFavoritesToFile(context, favorites);//save updated list
    }
}
