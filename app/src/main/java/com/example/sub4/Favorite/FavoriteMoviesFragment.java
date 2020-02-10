package com.example.sub4.Favorite;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sub4.Adapter.FavoriteAdapater;
import com.example.sub4.Database.FavoriteHelper;
import com.example.sub4.DetailFavorite;
import com.example.sub4.DetailMovies;
import com.example.sub4.Entity.Favorite;
import com.example.sub4.HomeNavigation.Favorite.FavoriteFragment;
import com.example.sub4.HomeNavigation.Movies.MoviesViewModel;
import com.example.sub4.Mapping.MappingHelper;
import com.example.sub4.Model.MoviesModel;
import com.example.sub4.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment implements LoadFavoriteCallback {

    private ProgressBar progressBar;
    private RecyclerView rvMoviesfavorite;
    private FavoriteAdapater favoriteAdapater;
    private FavoriteHelper favoriteHelper;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);

        //inisialisasi
        progressBar = view.findViewById(R.id.progress_bar_movies_favorite);
        rvMoviesfavorite = view.findViewById(R.id.rv_movies_favorite);
        favoriteAdapater = new FavoriteAdapater(this);


        //recycler view
        recyclerView();

        //inisialisasi add favorite
        favoriteHelper = FavoriteHelper.getInstance(getContext().getApplicationContext());
        favoriteHelper.open();

        if (savedInstanceState == null){
            new LoadFavoriteAsync(favoriteHelper,this).execute();
        } else {
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list!=null){
                favoriteAdapater.setListFavorite(list);
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(EXTRA_STATE,favoriteAdapater.getListFavorite());
    }

    @Override
    public void onStart() {
        super.onStart();
        favoriteAdapater.notifyDataSetChanged();
        if (favoriteAdapater == null){

            new LoadFavoriteAsync(favoriteHelper,this).execute();
        }else {
            ArrayList<Favorite> list = favoriteAdapater.getListFavorite();
            if (list!=null){
                favoriteAdapater.setListFavorite(list);
            }
        }

    }

    //    @Override
//    public void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.VISIBLE);
//        ArrayList<Favorite> favorites = favoriteAdapater.getListFavorite();
//        Log.d("Favorite", String.valueOf(favorites));
//
//
//        recyclerView();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

    private void recyclerView() {
        rvMoviesfavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMoviesfavorite.setHasFixedSize(true);
        rvMoviesfavorite.setAdapter(favoriteAdapater);
        favoriteAdapater.notifyDataSetChanged();
        Log.d("recycler", String.valueOf(rvMoviesfavorite));

        favoriteAdapater.setOnItemClickcallback(new FavoriteAdapater.OnItemClickcallback() {
            @Override
            public void onItemClicked(Favorite favorite) {
                Toast.makeText(getContext(),"Detail "+favorite.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailFavorite.class);
                intent.putExtra(DetailFavorite.EXTRA_DATA, favorite);
                startActivity(intent);
            }
        });
    }

    @Override
    public void preExecute() {
       getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void postExecute(ArrayList<Favorite> favorites) {

        progressBar.setVisibility(View.INVISIBLE);

        if (favorites.size()>0){
            favoriteAdapater.setListFavorite(favorites);
        }else {
            favoriteAdapater.setListFavorite(new ArrayList<Favorite>());
            Toast.makeText(getContext(), "Data not available", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadFavoriteAsync extends AsyncTask<Void, Void, ArrayList<Favorite>> {

        private final WeakReference<FavoriteHelper> weakFavoriteHelper;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        private LoadFavoriteAsync(FavoriteHelper favoriteHelper, LoadFavoriteCallback callback) {
            weakFavoriteHelper = new WeakReference<>(favoriteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Favorite> doInBackground(Void... voids) {
            Cursor dataCursor = weakFavoriteHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> favorites) {
            super.onPostExecute(favorites);
            weakCallback.get().postExecute(favorites);
        }
    }


}

interface LoadFavoriteCallback{
    void preExecute();
    void postExecute(ArrayList<Favorite> favorites);
}
