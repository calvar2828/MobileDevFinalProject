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
import com.example.finalproject1.models.MovieResponse;
import com.example.finalproject1.network.About;
import com.example.finalproject1.network.MovieApiService;
import com.example.finalproject1.network.RetrofitClient;
import com.example.finalproject1.network.favorites_movies;
import com.example.finalproject1.network.movie_Details;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
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

    //array of random words used to search movies
    private final String[] keywordList = {
            "Matrix", "Batman", "Avengers", "Iron", "Spider", "Superman", "Alien", "Predator", "Terminator", "Hunger",
            "Games", "Twilight", "Harry", "Potter", "Star", "Wars", "Trek", "Joker", "Deadpool", "Doctor",
            "Strange", "Captain", "Marvel", "America", "Guardians", "Galaxy", "Thor", "Ant", "Man", "Fast",
            "Furious", "Transformers", "Dune", "Inception", "Interstellar", "Django", "Shrek", "Frozen", "Encanto", "Moana",
            "Toy", "Story", "Up", "Soul", "Coco", "Inside", "Out", "Cars", "Nemo", "Finding",
            "Monsters", "University", "WALL", "E", "Ratatouille", "Zootopia", "Lion", "King", "Aladdin", "Tarzan",
            "Hercules", "Jumanji", "Jaws", "It", "Conjuring", "Annabelle", "Insidious", "Scream", "Saw", "Paranormal",
            "Activity", "Frozen", "Beauty", "Beast", "Cinderella", "Maleficent", "Cruella", "Luca", "Turning", "Red",
            "Wish", "Bolt", "Brave", "Coraline", "Sing", "Minions", "Despicable", "Me", "Puss", "Boots",
            "How", "Train", "Dragon", "Kung", "Panda", "Megamind", "Cloudy", "Chance", "Meatballs", "Tangled"
    };
    private String getRandomKeyword() {
        return keywordList[new Random().nextInt(keywordList.length)];
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

        // initialize UI components
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

        //Handle menu item clicks in the navigation drawer
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

    //pick a random word and use it to fetch a movie from the API
    private void fetchRandomMovie() {
        String randomTitle = getRandomKeyword();

        //setup retrofit client and make the api call
        MovieApiService apiService = RetrofitClient.getClient().create(MovieApiService.class);
        Call<Movie> call = apiService.getMovie(randomTitle, "7ce58d1c");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                //if the response is successful update the ui with movie data
                if (response.isSuccessful() && response.body() != null && response.body().getTitle() != null) {
                    Movie movie = response.body();
                    updateUI(movie);
                } else {
                    txtTitle.setText("No movie found for: " + randomTitle);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                //handle error in api call
                Log.e("API_ERROR", "Error getting the data: " + t.getMessage());
                txtTitle.setText("Error loading movie");
            }
        });
    }

    //takes the fetched movie to shows the data on the screen
    private void updateUI(Movie movie) {
        currentMovie = movie;
        txtTitle.setText(movie.getTitle());
        txtRating.setText("IMDb Rating: " + (movie.getImdbRating() != null? movie.getImdbRating() :"N/A"));

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
