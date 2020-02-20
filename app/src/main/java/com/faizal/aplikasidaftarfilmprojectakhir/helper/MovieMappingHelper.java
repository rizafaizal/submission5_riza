package com.faizal.aplikasidaftarfilmprojectakhir.helper;

import android.database.Cursor;

import com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;

import java.util.ArrayList;

public class MovieMappingHelper {

    public static ArrayList<Film> mapCursorToArrayList(Cursor MoviesItemsCursor) {
        ArrayList<Film> MoviesItemsList = new ArrayList<>();
        while (MoviesItemsCursor.moveToNext()) {
            int id = MoviesItemsCursor.getInt(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MovieColoumns._ID));
            String title = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MovieColoumns.TITLE_MV));
            String release = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MovieColoumns.RELEASE_MV));
            String overview = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MovieColoumns.OVERVIEW_MV));
            String score = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MovieColoumns.SCORE_MV));
            String poster = MoviesItemsCursor.getString(MoviesItemsCursor.getColumnIndexOrThrow(DatabaseContract.MovieColoumns.POSTER_MV));
            MoviesItemsList.add(new Film(id, title, release, overview, score, poster));
        }
        return MoviesItemsList;
    }

}
