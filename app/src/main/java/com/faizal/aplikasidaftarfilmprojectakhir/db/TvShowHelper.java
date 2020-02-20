package com.faizal.aplikasidaftarfilmprojectakhir.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TABLE_TVSHOW;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TVsColoumns.TITLE_TVS;

public class TvShowHelper {

    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper dataBaseHelper;
    private static TvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Boolean getOne(String name){
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TITLE_TVS+ " " + " LIKE " +"'"+name+"'" ;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(querySingleRecord,null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0 ){

            return true;
        }else if(cursor.getCount() == 0){
            return false;
        }
        return false;
    }

    public static long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteByTitle(String title) {
        return database.delete(DATABASE_TABLE, TITLE_TVS + " = ?", new String[]{title});
    }

}
