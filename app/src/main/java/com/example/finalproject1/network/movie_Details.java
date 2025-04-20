package com.example.finalproject1.network;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.finalproject1.MainActivity;
import com.example.finalproject1.R;
import com.example.finalproject1.models.Movie;
import com.example.finalproject1.utils.FavoritesManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.List;

public class movie_Details extends AppCompatActivity {

    private TextView txtTitle, txtRating, txtPlot, txtDirector, txtActors, txtYear;
    private ImageView imgPoster;
    private Movie movie;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_movie_details);

        txtTitle = findViewById(R.id.txt_movieTitle1);
        imgPoster = findViewById(R.id.img_moviePoster1);
        txtPlot = findViewById(R.id.txt_movieDescription1);
        txtDirector = findViewById(R.id.txt_movieDirector);
        txtActors = findViewById(R.id.txt_movieActors);
        txtYear = findViewById(R.id.txt_movieYear);

        drawerLayout = findViewById(R.id.drawer_layout_movie_details);
        ImageView btnMenu = findViewById(R.id.btn_menu_movie_details);

        btnMenu.setOnClickListener(v->{
            if(drawerLayout!=null ){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigation_view_movie_details);
        navigationView.setNavigationItemSelectedListener(item->{
            int itemId=item.getItemId();

            if(itemId == R.id.nav_favoritesMovies){
                Intent intent = new Intent(this, favorites_movies.class);
                startActivity(intent);

                drawerLayout.closeDrawers();
                return true;
            }
            if(itemId==R.id.nav_about){
                Intent intent =new Intent(this, About.class);
                startActivity(intent);

                drawerLayout.closeDrawers();
                return true;
            }
            if(itemId==R.id.nav_Main){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                drawerLayout.closeDrawers();
                return true;
            }
            return false;
        });

        //Display the details of a movie in the main
        movie = (Movie) getIntent().getSerializableExtra("movie_data");

        if (movie != null) {
            txtTitle.setText(movie.getTitle());
            txtPlot.setText(movie.getPlot());
            txtDirector.setText("Director: " + movie.getDirector());
            txtActors.setText("Actors: " + movie.getActors());
            txtYear.setText("Year: " + movie.getYear());

            Glide.with(this)
                    .load(movie.getPoster())
                    .into(imgPoster);
        }

        //to display the details when click on a movie in the favorite list
        Intent intentFavorites = getIntent();
        if (intentFavorites != null && intentFavorites.hasExtra("movie")) {
            movie = (Movie) intentFavorites.getSerializableExtra("movie");

            txtTitle.setText(movie.getTitle());
            txtPlot.setText(movie.getPlot());
            txtDirector.setText("Director: " + movie.getDirector());
            txtActors.setText("Actors: " + movie.getActors());
            txtYear.setText("Year: " + movie.getYear());

            Glide.with(this)
                    .load(movie.getPoster())
                    .into(imgPoster);
        }
    }



    public void accessMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void saveToFavorites(View view) {
        if (movie != null) {
            try {
                FileInputStream fis = openFileInput("favorites.json");
                byte[] data = new byte[fis.available()];
                fis.read(data);
                fis.close();

                String json = new String(data, "UTF-8");
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Movie>>() {}.getType();
                List<Movie> favorites = gson.fromJson(json, listType);

                for (Movie fav : favorites) {
                    if (fav.getTitle().equalsIgnoreCase(movie.getTitle())) {
                        Toast.makeText(this, "Movie already in favorites!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            } catch (Exception e) {
                // ignore, proceed to save
            }

            FavoritesManager.saveMovie(this, movie);
            Toast.makeText(this, "Movie saved to favorites!", Toast.LENGTH_SHORT).show();
        }
    }



}
