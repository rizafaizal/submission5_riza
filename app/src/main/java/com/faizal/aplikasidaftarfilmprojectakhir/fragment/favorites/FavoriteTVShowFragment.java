package com.faizal.aplikasidaftarfilmprojectakhir.fragment.favorites;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.db.TvShowHelper;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.interfaces.LoadTVsCallback;
import com.faizal.aplikasidaftarfilmprojectakhir.helper.TvShowMappingHelper;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.adapter.TVsAdapter;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVShowFragment extends Fragment implements LoadTVsCallback {

    private TVsAdapter adapter;
    private ProgressBar progressBarTvShowFavorit;
    private TvShowHelper tvShowHelperHelper;


    private ArrayList<Film> items = new ArrayList<>();
    private RecyclerView rvTvShow;


    public FavoriteTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_tvshow, container, false);
        rvTvShow = view.findViewById(R.id.rc_tvshow_favorite);
        rvTvShow.setHasFixedSize(true);

        progressBarTvShowFavorit = view.findViewById(R.id.progressbarTVShowFavorite);

        tvShowHelperHelper = TvShowHelper.getInstance(getContext());
        tvShowHelperHelper.open();


        showRecyclerView();

        new FavoriteTVShowFragment.LoadTvShowAsync(tvShowHelperHelper, this).execute();

        return view;
    }

    private void showRecyclerView() {
        adapter = new TVsAdapter();
        adapter.notifyDataSetChanged();
        rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShow.setAdapter(adapter);
    }

    @Override
    public void preExecute() {
        progressBarTvShowFavorit.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Film> tvShowItems) {
        progressBarTvShowFavorit.setVisibility(View.INVISIBLE);
        adapter.setData(tvShowItems);
        items.addAll(tvShowItems);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadTvShowAsync  extends AsyncTask<Void, Void, ArrayList<Film>> {

        private final WeakReference<TvShowHelper> weakTvShowHelper;
        private final WeakReference<LoadTVsCallback> weakCallback;

        private LoadTvShowAsync(TvShowHelper tvShowHelper, LoadTVsCallback callback) {
            weakTvShowHelper= new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Film> doInBackground(Void... voids) {
            Cursor dataCursor = weakTvShowHelper.get().queryAll();
            return TvShowMappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Film> tvShowItems) {
            super.onPostExecute(tvShowItems);

            weakCallback.get().postExecute(tvShowItems);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        tvShowHelperHelper.close();
    }
}
