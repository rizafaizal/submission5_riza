package com.faizal.aplikasidaftarfilmprojectakhir.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.faizal.aplikasidaftarfilmprojectakhir.db.MovieHelper;

import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.AUTHORITY;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.MovieColoumns.CONTENT_URI;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TABLE_MOVIE;

public class FavMoviesProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_TITLE = 2;
    private MovieHelper movieHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // content://com.faizal.aplikasidaftarfilmprojectakhir/movie
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        // content://com.faizal.aplikasidaftarfilmprojectakhir/movie/id
        sUriMatcher.addURI(AUTHORITY,
                TABLE_MOVIE + "/#",
                MOVIE_TITLE);
    }


    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings,
                        String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryAll();
                break;
            case MOVIE_TITLE:
                cursor = movieHelper.queryByName(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insert(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_TITLE:
                updated = movieHelper.update(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return updated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_TITLE:
                deleted = movieHelper.deleteByTitle(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return deleted;
    }

    public FavMoviesProvider() {
    }
}
