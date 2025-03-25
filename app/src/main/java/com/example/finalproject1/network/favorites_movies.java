package com.example.finalproject1.network;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject1.MainActivity;
import com.example.finalproject1.R;

public class favorites_movies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_favorites);
    }

    public void accessMain (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void accessMoviesDetails(View view){
        Intent intent = new Intent(this, movie_Details.class);
        startActivity(intent);

    }
}
