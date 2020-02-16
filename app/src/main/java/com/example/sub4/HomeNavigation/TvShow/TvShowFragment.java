package com.example.sub4.HomeNavigation.TvShow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sub4.Adapter.MoviesAdapter;
import com.example.sub4.Adapter.TvShowAdapter;
import com.example.sub4.DetailTvShow;
import com.example.sub4.HomeNavigation.Movies.MoviesViewModel;
import com.example.sub4.Model.TvShowModel;
import com.example.sub4.R;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {

    private TvShowViewModel tvShowViewModel;
    private ProgressBar progressBar;
    private RecyclerView rvTvShow;
    private TvShowAdapter tvShowAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show,container,false);

        //inisialisasi
        progressBar = view.findViewById(R.id.progress_bar_tv_show);
        rvTvShow = view.findViewById(R.id.rv_tv_show);
        tvShowAdapter = new TvShowAdapter();

        //koneksi ke viewModel
        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory()).get(TvShowViewModel.class);

        //proses data
        showLoading(true);
        viewModelCalled();
        recyclerView();

        return view;
    }


    private void viewModelCalled() {
        tvShowViewModel.setTvShow();

        tvShowViewModel.getTvShow().observe(this, new Observer<ArrayList<TvShowModel>>() {
            @Override
            public void onChanged(ArrayList<TvShowModel> tvShowModels) {
                if (tvShowModels!=null){
                    tvShowAdapter.setTvShowData(tvShowModels);
                    showLoading(false);
                }
            }
        });
    }

    private void recyclerView() {
        rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        tvShowAdapter.notifyDataSetChanged();
        rvTvShow.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowModel model) {
                Intent intent = new Intent(getContext(), DetailTvShow.class);
                intent.putExtra(DetailTvShow.EXTRA_DATA,model);
                startActivity(intent);
            }
        });
    }

    private void showLoading(boolean state) {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }


}