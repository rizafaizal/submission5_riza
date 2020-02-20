package com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FilmViewModel extends ViewModel {

    private static final String API_KEY = "022df932950ade918fbf7c740d31e834";
    private MutableLiveData<ArrayList<Film>> listFilm = new MutableLiveData<>();

    public void setMovies(final String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Film> listItems = new ArrayList<>();

        String urlMovies = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language="+language;

        client.get(urlMovies, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {

                        JSONObject jObj = list.getJSONObject(i);
                        Film movies = new Film();
                        movies.setId(jObj.getInt("id"));
                        movies.setTitle(jObj.getString("title"));
                        movies.setRelease(jObj.getString("release_date"));
                        movies.setOverview(jObj.getString("overview"));
                        movies.setScore(jObj.getString("vote_average"));
                        String poster= jObj.getString("poster_path" );
                        movies.setPoster("https://image.tmdb.org/t/p/w780" + poster);

                        listItems.add(movies);
                    }
                    listFilm.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

    }

    public LiveData<ArrayList<Film>> getMovies() {
        return listFilm;
    }

    public void setTvshow(final String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Film> listItems = new ArrayList<>();
        String urlTVs = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language="+language;

        client.get(urlTVs, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jObj = list.getJSONObject(i);
                        Film tvs = new Film();
                        tvs.setId(jObj.getInt("id"));
                        tvs.setTitle(jObj.getString("original_name"));
                        tvs.setRelease(jObj.getString("first_air_date"));
                        tvs.setOverview(jObj.getString("overview"));
                        tvs.setScore(jObj.getString("vote_average"));
                        String poster= jObj.getString("poster_path" );
                        tvs.setPoster("https://image.tmdb.org/t/p/w780" + poster);
                        listItems.add(tvs);
                    }
                    listFilm.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

    }

    public LiveData<ArrayList<Film>> getTvshow() {
        return listFilm;
    }

    public void setSearchMovies(final String qry) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Film> listItems = new ArrayList<>();

        String urlSearchMovies = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY +"&language=en-US&query="+qry;

        client.get(urlSearchMovies, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {

                        JSONObject jObj = list.getJSONObject(i);
                        Film movies = new Film();
                        movies.setId(jObj.getInt("id"));
                        movies.setTitle(jObj.getString("title"));
                        movies.setRelease(jObj.getString("release_date"));
                        movies.setOverview(jObj.getString("overview"));
                        movies.setScore(jObj.getString("vote_average"));
                        String poster= jObj.getString("poster_path" );
                        movies.setPoster("https://image.tmdb.org/t/p/w780" + poster);

                        listItems.add(movies);
                    }
                    listFilm.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

    }

    public LiveData<ArrayList<Film>> getSearchMovies() {
        return listFilm;
    }

    public void setSearchTVs(final String qry) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Film> listItems = new ArrayList<>();

        String urlSearchTVs = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY +"&language=en-US&query="+qry;

        client.get(urlSearchTVs, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {

                        JSONObject jObj = list.getJSONObject(i);
                        Film tvs = new Film();
                        tvs.setId(jObj.getInt("id"));
                        tvs.setTitle(jObj.getString("original_name"));
                        tvs.setRelease(jObj.getString("first_air_date"));
                        tvs.setOverview(jObj.getString("overview"));
                        tvs.setScore(jObj.getString("vote_average"));
                        String poster= jObj.getString("poster_path" );
                        tvs.setPoster("https://image.tmdb.org/t/p/w780" + poster);

                        listItems.add(tvs);
                    }
                    listFilm.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

    }

    public LiveData<ArrayList<Film>> getSearchTVs() {
        return listFilm;
    }
}
