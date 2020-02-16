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
import com.example.sub4.Adapter.FavoriteTvShowAdapter;
import com.example.sub4.Database.FavoriteHelper;
import com.example.sub4.Database.FavoriteHelper1;
import com.example.sub4.DetailFavoriteMovies;
import com.example.sub4.DetailFavoriteTvShow;
import com.example.sub4.DetailTvShow;
import com.example.sub4.Entity.Favorite;
import com.example.sub4.Entity.Favorite1;
import com.example.sub4.Interface.LoadFavoriteCallback;
import com.example.sub4.Interface.LoadFavoriteCallback1;
import com.example.sub4.Mapping.MappingHelper;
import com.example.sub4.Mapping.MappingHelper1;
import com.example.sub4.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class FavoriteTvShowFragment extends Fragment implements LoadFavoriteCallback1 {

    private ProgressBar progressBar;
    private RecyclerView rvTvShowFavorite;
    private FavoriteTvShowAdapter favoriteTvShowAdapater;
    private FavoriteHelper1 favoriteHelper1;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //inisialisasi
        progressBar = view.findViewById(R.id.progress_bar_tv_show_favorite);
        rvTvShowFavorite = view.findViewById(R.id.rv_tv_show_favorite);
        favoriteTvShowAdapater = new FavoriteTvShowAdapter();

        recyclerView();

        if (savedInstanceState!=null){
            ArrayList<Favorite1> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list!=null){
                favoriteTvShowAdapater.setListFavorite(list);
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(EXTRA_STATE, favoriteTvShowAdapater.getListFavorite());
    }

    private void recyclerView() {
        rvTvShowFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShowFavorite.setHasFixedSize(true);
        rvTvShowFavorite.setAdapter(favoriteTvShowAdapater);
        favoriteTvShowAdapater.notifyDataSetChanged();

        favoriteTvShowAdapater.setOnItemClickCallback1(new FavoriteTvShowAdapter.OnItemCallback1() {
            @Override
            public void onItemClicked(Favorite1 favorite1) {
                Toast.makeText(getContext(),"Detail "+favorite1.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailFavoriteTvShow.class);
                intent.putExtra(DetailFavoriteTvShow.EXTRA_DATA1, favorite1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //inisialisasi add favorite
        favoriteHelper1 = FavoriteHelper1.getInstance(getContext().getApplicationContext());
        favoriteHelper1.open1();

        new LoadFavoriteAsync1(favoriteHelper1,this).execute();

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
    public void postExecute(ArrayList<Favorite1> favorites) {

        progressBar.setVisibility(View.INVISIBLE);

        if (favorites.size()>0){
            favoriteTvShowAdapater.setListFavorite(favorites);
        }else {
            favoriteTvShowAdapater.setListFavorite(new ArrayList<Favorite1>());
            Toast.makeText(getContext(), "Data not available", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadFavoriteAsync1 extends AsyncTask<Void, Void, ArrayList<Favorite1>> {

        private final WeakReference<FavoriteHelper1> weakFavoriteHelper;
        private final WeakReference<LoadFavoriteCallback1> weakCallback;

        private LoadFavoriteAsync1(FavoriteHelper1 favoriteHelper, LoadFavoriteCallback1 callback) {
            weakFavoriteHelper = new WeakReference<>(favoriteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Favorite1> doInBackground(Void... voids) {
            Cursor dataCursor = weakFavoriteHelper.get().queryAll1();
            return MappingHelper1.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite1> favorites) {
            super.onPostExecute(favorites);
            weakCallback.get().postExecute(favorites);
        }
    }
}
