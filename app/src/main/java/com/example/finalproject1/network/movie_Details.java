package com.example.finalproject1.network;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.finalproject1.MainActivity;
import com.example.finalproject1.R;
import com.example.finalproject1.models.Movie;

public class movie_Details extends AppCompatActivity {

    private TextView txtTitle,txtRating,txtPlot,txtDirector,txtActors,txtYear;
    private ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_movie_details);

        txtTitle =findViewById(R.id.txt_movieTitle1);
        imgPoster=findViewById(R.id.img_moviePoster1);
        txtPlot=findViewById(R.id.txt_movieDescription1);
        txtDirector=findViewById(R.id.txt_movieDirector);
        txtActors=findViewById(R.id.txt_movieActors);
        txtYear=findViewById(R.id.txt_movieYear);


        Movie movie = (Movie) getIntent().getSerializableExtra("movie_data");
        if(movie!=null){
            txtTitle.setText(movie.getTitle());
            txtPlot.setText(movie.getPlot());
            txtDirector.setText("Director: " + movie.getDirector());
            txtActors.setText("Actors: "+ movie.getActors());
            txtYear.setText("Year: " + movie.getYear());



            Glide.with(this)
                    .load(movie.getPoster())
                    .into(imgPoster);
        }

    }

    public void accessMain(View view){
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
