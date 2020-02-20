package com.faizal.aplikasidaftarfilmprojectakhir.fragment.interfaces;

import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;

import java.util.ArrayList;

public interface LoadTVsCallback {

    void preExecute();
    void postExecute(ArrayList<Film> tvShowItems);

}
