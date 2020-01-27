package com.example.sub4.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sub4.Favorite.FavoriteMoviesFragment;
import com.example.sub4.Favorite.FavoriteTvShowFragment;
import com.example.sub4.HomeNavigation.Favorite.FavoriteFragment;
import com.example.sub4.R;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    public final FavoriteFragment mContext;

    public SectionPagerAdapter(@NonNull FragmentManager fm, FavoriteFragment context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    //halaman
    private final int[] TAB_TITLES = new int[]{
        R.string.text_movies,
        R.string.text_tv_show
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FavoriteMoviesFragment();
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
