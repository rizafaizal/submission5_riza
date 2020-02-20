package com.faizal.favoritefilm.activity.interfaces;

import com.faizal.favoritefilm.recyclerview.model.Film;

import java.util.ArrayList;

public interface LoadTVsCallback {

    void preExecute();
    void postExecute(ArrayList<Film> tvShowItems);

}
