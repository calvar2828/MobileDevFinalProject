package com.example.finalproject1.adapter;

import static com.example.finalproject1.utils.FavoritesManager.removeFromFavorites;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject1.R;
import com.example.finalproject1.models.Movie;
import com.example.finalproject1.network.movie_Details;

import java.util.List;


// This adapter class connects the list of favorite movies to the RecyclerView
public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.MovieViewHolder> {

    private List<Movie> movieList;//list of favorite movies
    private Context context;//context for ui operations

    //constructor for the adapter
    public FavoriteMoviesAdapter(List<Movie> movies, Context context) {
        this.movieList = movies;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_movie_item, parent, false);
        return new MovieViewHolder(view);
    }


    // This method binds movie data to the views
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.txtTitle.setText(movie.getTitle());
        holder.txtYear.setText("Year: " + movie.getYear());
        // Load movie poster image using Glide
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .into(holder.imgPoster);

        //on clicking the remove button remove the movie from favorites
        holder.btnRemove.setOnClickListener(v -> {
            removeFromFavorites(context,movie);
            movieList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
        // on clicking the item go to movie details screen
        holder.imgPoster.setOnClickListener(v->{
            Intent intentFavorites =new Intent(context, movie_Details.class);
            intentFavorites.putExtra("movie",movie);
            context.startActivity(intentFavorites);
        });
    }

    // Return the size of the movie list
    @Override
    public int getItemCount() {
        return movieList.size();
    }


    //viewHolder class holds references to the UI elements for each item
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtYear;
        ImageView imgPoster;
        Button btnRemove;

        public MovieViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_card_title);
            txtYear = itemView.findViewById(R.id.txt_card_year);
            imgPoster = itemView.findViewById(R.id.img_card_poster);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}