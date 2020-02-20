package com.faizal.aplikasidaftarfilmprojectakhir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.MoviesFragment;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.TvshowFragment;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.search.SearchFragmentMovies;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.search.SearchFragmentTVs;

public class SearchActivity extends AppCompatActivity {

    SearchView searchMovies;
    SearchView searchTVs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        getSupportActionBar().setTitle(getResources().getString(R.string.bar_search_film));

        loadFragment(new MoviesFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchMovies = (SearchView) (menu.findItem(R.id.searchMovies)).getActionView();
        searchTVs = (SearchView) (menu.findItem(R.id.searchTVs)).getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchMovies:
                setSearchMovies();
                searchMovies.setQuery( "",true);
                searchTVs.setVisibility(View.INVISIBLE);
                searchMovies.setVisibility(View.VISIBLE);
                loadFragment(new MoviesFragment());
                break;
            case R.id.searchTVs:
                setSearchTVs();
                searchTVs.setQuery( "",true);
                searchMovies.setVisibility(View.INVISIBLE);
                searchTVs.setVisibility(View.VISIBLE);
                loadFragment(new TvshowFragment());
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setSearchMovies() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {

            searchMovies.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchMovies.setQueryHint(getResources().getString(R.string.search_movies));
            searchMovies.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchMovies.clearFocus();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() > 0) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("query", newText);
                        SearchFragmentMovies fragment = new SearchFragmentMovies();
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.commit();
                    } else {
                        loadFragment(new MoviesFragment());
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.tab_movies);
                        }
                    }

                    return false;
                }
            });
        }
    }

    private void setSearchTVs() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            searchTVs.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchTVs.setQueryHint(getResources().getString(R.string.search_tvshow));
            searchTVs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchTVs.clearFocus();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() > 0) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("query", newText);
                        SearchFragmentTVs fragment = new SearchFragmentTVs();
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.commit();
                    } else {
                        loadFragment(new TvshowFragment());
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.tab_tvshow);
                        }
                    }

                    return false;
                }
            });
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
