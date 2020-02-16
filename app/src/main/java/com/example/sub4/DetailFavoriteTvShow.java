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
import com.example.sub4.Adapter.FavoriteTvShowAdapter;
import com.example.sub4.Database.FavoriteHelper;
import com.example.sub4.Database.FavoriteHelper1;
import com.example.sub4.Entity.Favorite;
import com.example.sub4.Entity.Favorite1;
import com.example.sub4.Favorite.FavoriteMoviesFragment;

import java.util.ArrayList;

public class DetailFavoriteTvShow extends AppCompatActivity {

    private ImageView ivPoster;
    private TextView tvTitle, tvDescription;
    private ProgressBar progressBar;
    private Button btnUnfavorite;

    private Favorite1 favorite;
    private int position;
    private FavoriteHelper1 favoriteHelper;
    private FavoriteTvShowAdapter favoriteTvShowAdapter;

    public static final String EXTRA_DATA1 = "extra_data";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_DELETE = 301;

    private ArrayList<Favorite1> favorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite_tv_show);

        ivPoster = findViewById(R.id.iv_favorite_poster_tv_show_detail);
        tvTitle = findViewById(R.id.tv_favorite_title_tv_show_detail);
        tvDescription = findViewById(R.id.tv_favorite_description_tv_show_detail);
        btnUnfavorite = findViewById(R.id.btn_unfavorite_tv_show_detail);
        progressBar = findViewById(R.id.pb_favorite_tv_show_detail);

        //inisilisasi
        favoriteHelper = FavoriteHelper1.getInstance(getApplicationContext());
        favoriteHelper.open1();
        favorite = getIntent().getParcelableExtra(EXTRA_DATA1);

        favoriteTvShowAdapter = new FavoriteTvShowAdapter();


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
                    long result = favoriteHelper.deleteById1(String.valueOf(favorite.getId()));
                    if (result>0){
                        Toast.makeText(DetailFavoriteTvShow.this,"Unfavorite : "+favorite.getTitle(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailFavoriteTvShow.this, FavoriteMoviesFragment.class);
                        intent.putExtra(EXTRA_POSITION,position);
                        setResult(RESULT_DELETE,intent);
                        if (favorites.size()>0){
                            favoriteTvShowAdapter.removeItem1(position);
                        }
                        finish();
                    }else {
                        Toast.makeText(DetailFavoriteTvShow.this,"Failed Unfavorite : ",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        showLoading(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteHelper.close1();
    }

    private void showLoading(boolean state) {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
