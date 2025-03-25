package com.example.finalproject1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.finalproject1.models.Movie;
import com.example.finalproject1.network.MovieApiService;
import com.example.finalproject1.network.RetrofitClient;
import com.example.finalproject1.network.favorites_movies;
import com.example.finalproject1.network.movie_Details;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Movie currentMovie;
    private TextView txtTitle, txtYear, txtDirector, txtActors, txtRating, txtPlot;
    private ImageView imgPoster;
    private final String[] randomWords = {"love", "war", "future", "dark", "happy", "space", "ghost", "magic"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_original);

        // Vincular los elementos de la UI
        imgPoster = findViewById(R.id.img_moviePoster);
        txtTitle = findViewById(R.id.txt_movieTitle);
        txtRating= findViewById(R.id.txt_movieRating);
        /*txtYear = findViewById(R.id.txt_year);
        txtDirector = findViewById(R.id.txt_director);
        txtActors = findViewById(R.id.txt_actors);
        txtRating = findViewById(R.id.txt_rating);*/
        //txtPlot = findViewById(R.id.txt_movieDesciption);

        // Obtener una película aleatoria
        fetchRandomMovie();


    }
    public void nextButton(View view){
        fetchRandomMovie();
    }
    private void fetchRandomMovie() {
        String randomQuery = randomWords[new Random().nextInt(randomWords.length)];

        MovieApiService apiService = RetrofitClient.getClient().create(MovieApiService.class);
        Call<Movie> call = apiService.getMovie(randomQuery, "7ce58d1c");  // 👈 Pasa la API Key aquí

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();
                    updateUI(movie);
                } else {
                    txtTitle.setText("No movie found");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("API_ERROR", "Error al obtener datos: " + t.getMessage());
                txtTitle.setText("No movie found");
            }
        });
    }

    private void updateUI(Movie movie) {
        currentMovie = movie;
        txtTitle.setText(movie.getTitle());
        txtRating.setText("IMDb Rating: " + (movie.getImdbRating() != null? movie.getImdbRating() :"N/A"));
        /*txtYear.setText("Year: " + movie.getYear());
        txtDirector.setText("Director: " + (movie.getDirector() != null ? movie.getDirector() : "N/A"));
        txtActors.setText("Actors: " + (movie.getActors() != null ? movie.getActors() : "N/A"));
        txtRating.setText("IMDb Rating: " + (movie.getImdbRating() != null ? movie.getImdbRating() : "N/A"));
        txtPlot.setText(movie.getPlot());*/

        // Cargar imagen con Glide
        Glide.with(this)
                .load(movie.getPoster())
                .into(imgPoster);
    }



    public void accessMoviesDetails(View view){
        if(currentMovie!=null){
            Intent intent = new Intent(this, movie_Details.class);
            intent.putExtra("movie_data",currentMovie);
            startActivity(intent);
        }

    }

    public void accessFavoritesMovies(View view){
        Intent intent = new Intent(this, favorites_movies.class);
        startActivity(intent);
    }
}
