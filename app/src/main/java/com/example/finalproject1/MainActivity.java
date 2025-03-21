package com.example.finalproject1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.finalproject1.models.Movie;
import com.example.finalproject1.models.MovieResponse;
import com.example.finalproject1.network.MovieApiService;
import com.example.finalproject1.network.RetrofitClient;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
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
        txtRating= findViewById(R.id.txt_movieDesciption);
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


}
