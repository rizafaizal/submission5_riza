package com.faizal.aplikasidaftarfilmprojectakhir.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.adapter.MoviesAdapter;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.viewmodel.FilmViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private MoviesAdapter adapter;
    private ProgressBar progressBar;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView rcMovies = view.findViewById(R.id.rc_movies);
        rcMovies.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBarMovie);

        rcMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));

        String language = getString(R.string.language);

        adapter = new MoviesAdapter();
        adapter.notifyDataSetChanged();
        rcMovies.setAdapter(adapter);

        FilmViewModel moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);
        moviesViewModel.setMovies(language);

        showLoading(true);

        moviesViewModel.getMovies().observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> moviesItems) {
                if (moviesItems != null) {
                    adapter.setData(moviesItems);
                    showLoading(false);
                }
            }
        });

        return view;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
