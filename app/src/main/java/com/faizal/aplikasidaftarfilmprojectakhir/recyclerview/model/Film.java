package com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.MovieColoumns.OVERVIEW_MV;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.MovieColoumns.POSTER_MV;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.MovieColoumns.RELEASE_MV;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.MovieColoumns.SCORE_MV;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.MovieColoumns.TITLE_MV;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.getColumnInt;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.getColumnString;

public class Film implements Parcelable {

    private int id;
    private String title;
    private String release;
    private String overview;
    private String score;
    private String poster;


    public Film () {
    }

    public Film(int id, String title, String release, String overview, String score, String poster) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.overview = overview;
        this.score = score;
        this.poster = poster;
    }

    public Film(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE_MV);
        this.poster = getColumnString(cursor, POSTER_MV);
        this.release = getColumnString(cursor, RELEASE_MV);
        this.score = getColumnString(cursor, SCORE_MV);
        this.overview = getColumnString(cursor, OVERVIEW_MV);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.overview);
        dest.writeString(this.score);
        dest.writeString(this.poster);
    }

    public Film(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release = in.readString();
        this.overview = in.readString();
        this.score = in.readString();
        this.poster = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
