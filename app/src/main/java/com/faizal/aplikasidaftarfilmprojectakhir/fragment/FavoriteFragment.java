package com.faizal.aplikasidaftarfilmprojectakhir.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.pager.PagerAdapterFavorite;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        PagerAdapterFavorite sectionsPagerAdapter = new PagerAdapterFavorite(getContext(), getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager_favorite);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tab_favorite);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

}
