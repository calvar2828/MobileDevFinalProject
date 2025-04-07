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

public class About extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.about);//to load the layout
        drawerLayout = findViewById(R.id.drawer_layout_about);
        ImageView btnMenu = findViewById(R.id.btn_menu_about);

        btnMenu.setOnClickListener(v->{
            if(drawerLayout!=null){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView= findViewById(R.id.navigation_view_about);
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


}
