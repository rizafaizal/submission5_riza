package com.faizal.favoritefilm.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.faizal.aplikasidaftarfilmprojectakhir";
    private static final String SCHEME = "content";

    public static String TABLE_MOVIE = "movie";
    public static final class MovieColoumns implements BaseColumns {
        public static String TITLE_MV = "title_mv";
        public static String RELEASE_MV = "release_mv";
        public static String OVERVIEW_MV = "overview_mv";
        public static String SCORE_MV = "score_mv";
        public static String POSTER_MV = "poster_mv";

        // untuk membuat URI content://"com.faizal.aplikasidaftarfilmprojectakhir/movie
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    static String TABLE_TVSHOW = "tvshow";
    public  static final class TVsColoumns implements BaseColumns {
        public static String TITLE_TVS = "title_tvs";
        public static String RELEASE_TVS = "release_tvs";
        public static String OVERVIEW_TVS = "overview_tvs";
        public static String SCORE_TVS = "score_tvs";
        public static String POSTER_TVS = "poster_tvs";

    }

}
