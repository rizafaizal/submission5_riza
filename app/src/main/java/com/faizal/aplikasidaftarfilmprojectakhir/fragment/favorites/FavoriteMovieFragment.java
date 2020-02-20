package com.faizal.aplikasidaftarfilmprojectakhir.fragment.favorites;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract;
import com.faizal.aplikasidaftarfilmprojectakhir.db.MovieHelper;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.interfaces.LoadMoviesCallback;
import com.faizal.aplikasidaftarfilmprojectakhir.helper.MovieMappingHelper;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.adapter.MoviesAdapter;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadMoviesCallback {

    private MoviesAdapter adapter;
    private ProgressBar progressBarMoviesFavorit;
    private MovieHelper movieHelper;

    private ArrayList<Film> items = new ArrayList<>();
    private RecyclerView rvMovies;


    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        rvMovies = view.findViewById(R.id.rc_movies_favorite);
        rvMovies.setHasFixedSize(true);

        progressBarMoviesFavorit = view.findViewById(R.id.progressbarMovieFavorite);

//        movieHelper = MovieHelper.getInstance(getContext());
//        movieHelper.open();

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(DatabaseContract.MovieColoumns.CONTENT_URI, true, myObserver);

        showRecyclerView();

//        new FavoriteMovieFragment.LoadMoviesAsync(movieHelper, this).execute();
        new FavoriteMovieFragment.LoadMoviesAsync(getContext(), this).execute();

        return view;
    }

    private void showRecyclerView() {
        adapter = new MoviesAdapter();
        adapter.notifyDataSetChanged();
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(adapter);
    }

    @Override
    public void preExecute() {
        progressBarMoviesFavorit.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Film> movies) {
        progressBarMoviesFavorit.setVisibility(View.INVISIBLE);
        adapter.setData(movies);
        items.addAll(movies);
    }

    @SuppressLint("StaticFieldLeak")
    private static class LoadMoviesAsync  extends AsyncTask<Void, Void, ArrayList<Film>> {

//        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;
        private final WeakReference<Context> weakContext;

//        private LoadMoviesAsync(MovieHelper movieHelper, LoadMoviesCallback callback) {
//            weakMovieHelper = new WeakReference<>(movieHelper);
//            weakCallback = new WeakReference<>(callback);
//        }
        private LoadMoviesAsync(Context context, LoadMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Film> doInBackground(Void... voids) {
//            Cursor dataCursor = weakMovieHelper.get().queryAll();
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.MovieColoumns.CONTENT_URI, null, null, null, null);
            return MovieMappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Film> films) {
            super.onPostExecute(films);

            weakCallback.get().postExecute(films);

        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMoviesAsync(context, (LoadMoviesCallback) context).execute();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        movieHelper.close();
    }
}
