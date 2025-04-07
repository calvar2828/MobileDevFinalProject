package com.example.finalproject1.network;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.finalproject1.MainActivity;
import com.example.finalproject1.R;
import com.google.android.material.navigation.NavigationView;

public class favorites_movies extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_favorites);//to load the layout

        drawerLayout = findViewById(R.id.drawer_layout_favorites);
        ImageView btnMenu = findViewById(R.id.btn_menu_favorites);

        btnMenu.setOnClickListener(v->{
            if(drawerLayout!=null){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView= findViewById(R.id.navigation_view_favorites);
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
    }

    //with this function sends the user back to the main screen
    public void accessMain (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //opens the movie details screen
    public void accessMoviesDetails(View view){
        Intent intent = new Intent(this, movie_Details.class);
        startActivity(intent);

    }
}
