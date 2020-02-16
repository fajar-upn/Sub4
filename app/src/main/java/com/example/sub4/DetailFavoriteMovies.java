package com.example.sub4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sub4.Adapter.FavoriteMoviesAdapater;
import com.example.sub4.Database.FavoriteHelper;
import com.example.sub4.Entity.Favorite;
import com.example.sub4.Favorite.FavoriteMoviesFragment;

import java.util.ArrayList;

public class DetailFavoriteMovies extends AppCompatActivity{

    private ImageView ivPoster;
    private TextView tvTitle, tvDescription;
    private ProgressBar progressBar;
    private Button btnUnfavorite;

    private Favorite favorite;
    private int position;
    private FavoriteHelper favoriteHelper;
    private FavoriteMoviesAdapater favoriteMoviesAdapater;


    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_DELETE = 301;

    private ArrayList<Favorite> favorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite_movies);

        ivPoster = findViewById(R.id.iv_favorite_poster_movies_detail);
        tvTitle = findViewById(R.id.tv_favorite_title_movies_detail);
        tvDescription = findViewById(R.id.tv_favorite_description_movies_detail);
        btnUnfavorite = findViewById(R.id.btn_unfavorite_movies_detail);
        progressBar = findViewById(R.id.pb_favorite_movies_detail);

        //inisilisasi
        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();
        favorite = getIntent().getParcelableExtra(EXTRA_DATA);
        favoriteMoviesAdapater = new FavoriteMoviesAdapater(this);



        showLoading(true);

        if (favorite!=null){
            String poster = favorite.getPoster();
            String title = favorite.getTitle();
            String description = favorite.getDescription();

            position = getIntent().getIntExtra(EXTRA_POSITION,0);

            Glide.with(this).load(poster).into(ivPoster);
            tvTitle.setText(title);
            tvDescription.setText(description);

            btnUnfavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long result = favoriteHelper.deleteById(String.valueOf(favorite.getId()));
                    if (result>0){
                        Toast.makeText(DetailFavoriteMovies.this,"Unfavorite : "+favorite.getTitle(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailFavoriteMovies.this,FavoriteMoviesFragment.class);
                        intent.putExtra(EXTRA_POSITION,position);
                        setResult(RESULT_DELETE,intent);
                        if (favorites.size()>0){
                            favoriteMoviesAdapater.removeItem(position);
                        }
                        finish();
                    }else {
                        Toast.makeText(DetailFavoriteMovies.this,"Failed Unfavorite : ",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        showLoading(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

    private void showLoading(boolean state) {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
