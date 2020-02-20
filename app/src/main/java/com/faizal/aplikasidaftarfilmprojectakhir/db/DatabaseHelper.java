package com.faizal.aplikasidaftarfilmprojectakhir.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TABLE_MOVIE;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TABLE_TVSHOW;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "dbfavorit";
    private static final int DB_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL )",
            TABLE_MOVIE,
            DatabaseContract.MovieColoumns._ID,
            DatabaseContract.MovieColoumns.TITLE_MV,
            DatabaseContract.MovieColoumns.RELEASE_MV,
            DatabaseContract.MovieColoumns.OVERVIEW_MV,
            DatabaseContract.MovieColoumns.SCORE_MV,
            DatabaseContract.MovieColoumns.POSTER_MV
    );

    private static final String SQL_CREATE_TABLE_TVS= String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL ,"+
                    "%s TEXT NOT NULL )",
            TABLE_TVSHOW,
            DatabaseContract.TVsColoumns._ID,
            DatabaseContract.TVsColoumns.TITLE_TVS,
            DatabaseContract.TVsColoumns.RELEASE_TVS,
            DatabaseContract.TVsColoumns.OVERVIEW_TVS,
            DatabaseContract.TVsColoumns.SCORE_TVS,
            DatabaseContract.TVsColoumns.POSTER_TVS
    );


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TVSHOW);

        onCreate(db);
    }
}
