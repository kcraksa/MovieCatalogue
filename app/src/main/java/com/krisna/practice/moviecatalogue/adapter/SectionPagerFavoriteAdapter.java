package com.krisna.practice.moviecatalogue.adapter;

import android.content.Context;

import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.ui.favorite.FavoriteMovieFragment;
import com.krisna.practice.moviecatalogue.ui.favorite.FavoriteTvShowFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPagerFavoriteAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionPagerFavoriteAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMovieFragment();
                break;

            case 1:
                fragment = new FavoriteTvShowFragment();
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
        return 2;
    }
}
