package com.example.sub4.Favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.sub4.Adapter.FavoriteAdapater;
import com.example.sub4.Database.FavoriteHelper;
import com.example.sub4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView rvMoviesfavorite;
    private FavoriteAdapater favoriteAdapater;
    private FavoriteHelper favoriteHelper;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }
}
