package com.example.finalproject1.adapter;

import android.content.Context;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.List;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Context context;

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

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.txtTitle.setText(movie.getTitle());
        holder.txtYear.setText("Year: " + movie.getYear());
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .into(holder.imgPoster);

        holder.btnRemove.setOnClickListener(v -> {
            removeMovieFromFavorites(movie);
            movieList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    private void removeMovieFromFavorites(Movie movie) {
        try {
            FileInputStream fis = context.openFileInput("favorites.json");
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();

            String json = new String(data, "UTF-8");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Movie>>() {}.getType();
            List<Movie> favorites = gson.fromJson(json, listType);

            for (int i = 0; i < favorites.size(); i++) {
                if (favorites.get(i).getTitle().equalsIgnoreCase(movie.getTitle())) {
                    favorites.remove(i);
                    break;
                }
            }

            String updatedJson = gson.toJson(favorites);
            FileOutputStream fos = context.openFileOutput("favorites.json", Context.MODE_PRIVATE);
            fos.write(updatedJson.getBytes());
            fos.close();

            Toast.makeText(context, "Movie removed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error removing movie", Toast.LENGTH_SHORT).show();
        }
    }

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