package com.faizal.aplikasidaftarfilmprojectakhir.pager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.FavoriteFragment;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.MoviesFragment;
import com.faizal.aplikasidaftarfilmprojectakhir.fragment.TvshowFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }


    @StringRes
    private final int[] TAB_TITLES = new int[] {
            R.string.tab_movies,
            R.string.tab_tvshow,
            R.string.tab_favorite
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MoviesFragment();
                break;
            case 1:
                fragment = new TvshowFragment();
                break;
            case 2:
                fragment = new FavoriteFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
