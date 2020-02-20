package com.faizal.favoritefilm.recyclerview.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizal.favoritefilm.R;
import com.faizal.favoritefilm.recyclerview.model.Film;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private ArrayList<Film> moviesData = new ArrayList<>();

    public void setData(ArrayList<Film> items) {
        moviesData.clear();
        moviesData.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new MoviesViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesViewHolder holder, final int position) {
        holder.bind(moviesData.get(position));

//        holder.cvFilm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Film movie = moviesData.get(position);
//                movie.setTitle(movie.getTitle());
//                movie.setRelease(movie.getRelease());
//                movie.setOverview(movie.getOverview());
//                movie.setScore(movie.getScore());
//                movie.setPoster(movie.getPoster());
//
//                Intent moveDetailMovies = new Intent(v.getContext(), DetailActivityMovies.class);
//                moveDetailMovies.putExtra(DetailActivityMovies.EXTRA_MOVIE, movie);
//                v.getContext().startActivity(moveDetailMovies);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtRelease, txtOverview, txtScore;
        CardView cvFilm;
        ImageView imgPoster;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRelease = itemView.findViewById(R.id.txt_release);
            txtScore = itemView.findViewById(R.id.txt_score);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            imgPoster = itemView.findViewById(R.id.img_poster);
            cvFilm = itemView.findViewById(R.id.cvFilm);
        }

        void bind(Film movie) {
            txtTitle.setText(movie.getTitle());
            txtRelease.setText(movie.getRelease());
            txtScore.setText(String.valueOf(movie.getScore()));
            txtOverview.setText(movie.getOverview());
            Glide.with(itemView.getContext())
                    .load(movie.getPoster())
                    .apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);
        }
    }
}
