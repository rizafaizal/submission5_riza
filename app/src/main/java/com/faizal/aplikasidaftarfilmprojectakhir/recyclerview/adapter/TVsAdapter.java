package com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.adapter;

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
import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.activity.DetailActivityTVShow;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;

import java.util.ArrayList;

public class TVsAdapter extends RecyclerView.Adapter<TVsAdapter.TVsViewHolder> {

    private ArrayList<Film> tVsData = new ArrayList<>();

    public void setData(ArrayList<Film> items) {
        tVsData.clear();
        tVsData.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TVsAdapter.TVsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new TVsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TVsAdapter.TVsViewHolder holder, final int position) {
        holder.bind(tVsData.get(position));

        holder.cvFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Film tvs = tVsData.get(position);
                tvs.setTitle(tvs.getTitle());
                tvs.setRelease(tvs.getRelease());
                tvs.setOverview(tvs.getOverview());
                tvs.setScore(tvs.getScore());
                tvs.setPoster(tvs.getPoster());

                Intent moveDetailTVs = new Intent(v.getContext(), DetailActivityTVShow.class);
                moveDetailTVs.putExtra(DetailActivityTVShow.EXTRA_TVs, tvs);
                v.getContext().startActivity(moveDetailTVs);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tVsData.size();
    }

    public class TVsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtRelease, txtOverview, txtScore;
        CardView cvFilm;
        ImageView imgPoster;

        public TVsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRelease = itemView.findViewById(R.id.txt_release);
            txtScore = itemView.findViewById(R.id.txt_score);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            imgPoster = itemView.findViewById(R.id.img_poster);
            cvFilm = itemView.findViewById(R.id.cvFilm);
        }

        void bind(Film tvs) {
            txtTitle.setText(tvs.getTitle());
            txtRelease.setText(tvs.getRelease());
            txtScore.setText(String.valueOf(tvs.getScore()));
            txtOverview.setText(tvs.getOverview());
            Glide.with(itemView.getContext())
                    .load(tvs.getPoster())
                    .apply(new RequestOptions().override(350, 550))
                    .into(imgPoster);
        }
    }
}
