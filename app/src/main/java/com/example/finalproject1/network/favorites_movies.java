package com.example.finalproject1.network;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject1.MainActivity;
import com.example.finalproject1.R;
import com.example.finalproject1.adapter.FavoriteMoviesAdapter;
import com.example.finalproject1.models.Movie;
import com.example.finalproject1.utils.FavoritesManager;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class favorites_movies extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private FavoriteMoviesAdapter adapter;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_favorites); // actual layout con RecyclerView

        //initialize drawer layout and navigation view
        drawerLayout = findViewById(R.id.drawer_layout_favorites);
        ImageView btnMenu = findViewById(R.id.btn_menu_favorites);




        btnMenu.setOnClickListener(v -> {
            if(drawerLayout != null){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        //navigation options
        NavigationView navigationView = findViewById(R.id.navigation_view_favorites);
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if(itemId == R.id.nav_favoritesMovies){
                startActivity(new Intent(this, favorites_movies.class));
                drawerLayout.closeDrawers();
                return true;
            }
            if(itemId == R.id.nav_about){
                startActivity(new Intent(this, About.class));
                drawerLayout.closeDrawers();
                return true;
            }
            if(itemId == R.id.nav_Main){
                startActivity(new Intent(this, MainActivity.class));
                drawerLayout.closeDrawers();
                return true;
            }
            return false;
        });
        //to show the list of favorite movies
        recyclerView = findViewById(R.id.img_recyclerViewMovie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Get the list of favorite movies from the FavoritesManager
        List<Movie> favoriteMovies = FavoritesManager.getFavorites(this);

        //set the adapter to display the favorite movies in the recyclerview
        adapter = new FavoriteMoviesAdapter(favoriteMovies,this);
        recyclerView.setAdapter(adapter);


    }


    public void accessMain(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void accessMoviesDetails(View view){
        startActivity(new Intent(this, movie_Details.class));
    }
}
