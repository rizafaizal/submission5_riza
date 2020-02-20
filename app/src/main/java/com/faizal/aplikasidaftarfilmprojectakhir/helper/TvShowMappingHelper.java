package com.faizal.aplikasidaftarfilmprojectakhir.helper;

import android.database.Cursor;

import com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;

import java.util.ArrayList;

public class TvShowMappingHelper {

    public static ArrayList<Film> mapCursorToArrayList(Cursor TvShowItemsCursor) {
        ArrayList<Film> TvShowItemList = new ArrayList<>();
        while (TvShowItemsCursor.moveToNext()) {
            int id = TvShowItemsCursor.getInt(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.TVsColoumns._ID));
            String title = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.TVsColoumns.TITLE_TVS));
            String release = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.TVsColoumns.RELEASE_TVS));
            String overview = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.TVsColoumns.OVERVIEW_TVS));
            String score = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.TVsColoumns.SCORE_TVS));
            String poster = TvShowItemsCursor.getString(TvShowItemsCursor.getColumnIndexOrThrow(DatabaseContract.TVsColoumns.POSTER_TVS));
            TvShowItemList.add(new Film(id, title, release, overview, score, poster));
        }
        return TvShowItemList;
    }

}
