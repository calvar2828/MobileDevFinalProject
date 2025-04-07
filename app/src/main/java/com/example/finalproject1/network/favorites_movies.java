package com.example.finalproject1.network;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_favorite_movie); // actual layout con RecyclerView

        drawerLayout = findViewById(R.id.drawer_layout_favorites);
        ImageView btnMenu = findViewById(R.id.btn_menu_favorites);
        recyclerView = findViewById(R.id.img_recyclerViewMovie);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Movie> favoriteMovies = FavoritesManager.getFavorites(this);
        adapter = new FavoriteMoviesAdapter(favoriteMovies,this);
        recyclerView.setAdapter(adapter);

        btnMenu.setOnClickListener(v -> {
            if(drawerLayout != null){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

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
    }

    public void accessMain(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void accessMoviesDetails(View view){
        startActivity(new Intent(this, movie_Details.class));
    }
}
