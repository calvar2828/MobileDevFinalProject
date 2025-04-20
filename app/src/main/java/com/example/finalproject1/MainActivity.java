package com.example.finalproject1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.finalproject1.models.Movie;
import com.example.finalproject1.network.About;
import com.example.finalproject1.network.MovieApiService;
import com.example.finalproject1.network.RetrofitClient;
import com.example.finalproject1.network.favorites_movies;
import com.example.finalproject1.network.movie_Details;
import com.google.android.material.navigation.NavigationView;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //this will store the current movie... with this i can pass it to another activity
    private Movie currentMovie;

    //UI elements
    private TextView txtTitle, txtYear, txtDirector, txtActors, txtRating, txtPlot;
    private ImageView imgPoster;

    private DrawerLayout drawerLayout;

    //random words to use it to fetch movies randomly
    private final String[] randomWords = {"love", "war", "future", "dark", "happy", "space", "ghost", "magic"};
    private String getRandomLetterQuery() {
        char letter = (char) ('a' + new Random().nextInt(26));
        return String.valueOf(letter);
    }
    private static boolean showToast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_original);

        if(!showToast){
            Toast.makeText(this, "Click on the poster for details", Toast.LENGTH_SHORT).show();
            showToast = true;
        }

        // link elements
        imgPoster = findViewById(R.id.img_moviePoster);
        txtTitle = findViewById(R.id.txt_movieTitle);
        txtRating= findViewById(R.id.txt_movieRating);
        /*txtYear = findViewById(R.id.txt_year);
        txtDirector = findViewById(R.id.txt_director);
        txtActors = findViewById(R.id.txt_actors);
        txtRating = findViewById(R.id.txt_rating);*/
        //txtPlot = findViewById(R.id.txt_movieDesciption);

        drawerLayout = findViewById(R.id.drawer_layout_main);
        ImageView btnMenu = findViewById(R.id.btn_menu);

        btnMenu.setOnClickListener(v->{
            if(drawerLayout!=null){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView= findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId=item.getItemId();

            if(itemId == R.id.nav_favoritesMovies){
                Intent intent = new Intent(this, favorites_movies.class);
                startActivity(intent);

                drawerLayout.closeDrawers();
                return true;
            }
            if(itemId ==R.id.nav_about){
                Intent intent= new Intent(this, About.class);
                startActivity(intent);

                drawerLayout.closeDrawers();
                return true;
            }
            if(itemId == R.id.nav_Main){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                drawerLayout.closeDrawers();
                return true;
            }
            return false;
        });
        // load the random movie
        fetchRandomMovie();
    }

    //Fetch the next movie with the button next
    public void nextButton(View view){
        fetchRandomMovie();
    }
    private void fetchRandomMovie() {
        String randomQuery = getRandomLetterQuery(); // ahora usamos una letra aleatoria

        MovieApiService apiService = RetrofitClient.getClient().create(MovieApiService.class);
        Call<Movie> call = apiService.getMovie(randomQuery, "7ce58d1c"); // tu API key
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getTitle() != null) {
                    Movie movie = response.body();
                    updateUI(movie);
                } else {
                    txtTitle.setText("No movie found. Try again!");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("API_ERROR", "Error getting the data: " + t.getMessage());
                txtTitle.setText("Error loading movie");
            }
        });
    }

    /*private void fetchRandomMovie() {
        String randomQuery = randomWords[new Random().nextInt(randomWords.length)];
        //setup for retrofit and the api call
        MovieApiService apiService = RetrofitClient.getClient().create(MovieApiService.class);
        Call<Movie> call = apiService.getMovie(randomQuery, "7ce58d1c");  // key api
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                //if the response is successful and not empty just upadte the UI
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();
                    updateUI(movie);
                } else {
                    txtTitle.setText("No movie found");
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("API_ERROR", "Error getting the data: " + t.getMessage());
                txtTitle.setText("No movie found");
            }
        });
    }*/

    //takes the fetched movie to shows the data on the screen
    private void updateUI(Movie movie) {
        currentMovie = movie;
        txtTitle.setText(movie.getTitle());
        txtRating.setText("IMDb Rating: " + (movie.getImdbRating() != null? movie.getImdbRating() :"N/A"));
        /*txtYear.setText("Year: " + movie.getYear());
        txtDirector.setText("Director: " + (movie.getDirector() != null ? movie.getDirector() : "N/A"));
        txtActors.setText("Actors: " + (movie.getActors() != null ? movie.getActors() : "N/A"));
        txtRating.setText("IMDb Rating: " + (movie.getImdbRating() != null ? movie.getImdbRating() : "N/A"));
        txtPlot.setText(movie.getPlot());*/

        // glide is uses to upload an image
        Glide.with(this)
                .load(movie.getPoster())
                .into(imgPoster);
    }


    //goes to movies details and send the current movie
    public void accessMoviesDetails(View view){
        if(currentMovie!=null){
            Intent intent = new Intent(this, movie_Details.class);
            intent.putExtra("movie_data",currentMovie);
            startActivity(intent);
        }
    }

    //goes to favorites screen
    public void accessFavoritesMovies(View view){
        Intent intent = new Intent(this, favorites_movies.class);
        startActivity(intent);
    }
}
