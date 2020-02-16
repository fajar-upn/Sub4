package com.example.sub4.Favorite;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sub4.Adapter.FavoriteMoviesAdapater;
import com.example.sub4.Database.FavoriteHelper;
import com.example.sub4.DetailFavoriteMovies;
import com.example.sub4.Entity.Favorite;
import com.example.sub4.Interface.LoadFavoriteCallback;
import com.example.sub4.Mapping.MappingHelper;
import com.example.sub4.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment implements LoadFavoriteCallback {

    private ProgressBar progressBar;
    private RecyclerView rvMoviesfavorite;
    private FavoriteMoviesAdapater favoriteMoviesAdapater;
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //inisialisasi
        progressBar = view.findViewById(R.id.progress_bar_movies_favorite);
        rvMoviesfavorite = view.findViewById(R.id.rv_movies_favorite);
        favoriteMoviesAdapater = new FavoriteMoviesAdapater(this);

        //recycler view
        recyclerView();

        if (savedInstanceState!=null){
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list!=null){
                favoriteMoviesAdapater.setListFavorite(list);
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(EXTRA_STATE, favoriteMoviesAdapater.getListFavorite());
    }

    private void recyclerView() {
        rvMoviesfavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMoviesfavorite.setHasFixedSize(true);
        rvMoviesfavorite.setAdapter(favoriteMoviesAdapater);
        favoriteMoviesAdapater.notifyDataSetChanged();

        favoriteMoviesAdapater.setOnItemClickcallback(new FavoriteMoviesAdapater.OnItemClickcallback() {
            @Override
            public void onItemClicked(Favorite favorite) {
                Toast.makeText(getContext(),"Detail "+favorite.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailFavoriteMovies.class);
                intent.putExtra(DetailFavoriteMovies.EXTRA_DATA, favorite);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        //inisialisasi add favorite
        favoriteHelper = FavoriteHelper.getInstance(getContext().getApplicationContext());
        favoriteHelper.open();
        new LoadFavoriteAsync(favoriteHelper,this).execute();

        //memanggil recycler view
        recyclerView();
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
            favoriteMoviesAdapater.setListFavorite(favorites);
        }else {
            favoriteMoviesAdapater.setListFavorite(new ArrayList<Favorite>());
            Toast.makeText(getContext(), "Data not available", Toast.LENGTH_SHORT).show();
        }

        favoriteHelper.close();
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
