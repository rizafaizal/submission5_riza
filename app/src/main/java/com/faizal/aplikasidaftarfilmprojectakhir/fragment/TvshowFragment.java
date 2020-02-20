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
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.adapter.TVsAdapter;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.viewmodel.FilmViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {

    private TVsAdapter adapter;
    private ProgressBar progressBar;

    public TvshowFragment() {
        // Required empty public constructor
    }


    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        RecyclerView rcTVs = view.findViewById(R.id.rc_tvshow);
        rcTVs.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBar_TVShow);

        rcTVs.setLayoutManager(new LinearLayoutManager(this.getContext()));

        String language = getString(R.string.language);

        adapter = new TVsAdapter();
        adapter.notifyDataSetChanged();
        rcTVs.setAdapter(adapter);

        FilmViewModel tVsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);
        tVsViewModel.setTvshow(language);

        showLoading(true);

        tVsViewModel.getTvshow().observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> tVsItems) {
                if (tVsItems != null) {
                    adapter.setData(tVsItems);
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
