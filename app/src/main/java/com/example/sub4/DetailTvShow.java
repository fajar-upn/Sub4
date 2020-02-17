package com.example.sub4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.sub4.Model.TvShowModel;

import static com.example.sub4.Database.DatabaseContract.FavoriteColoums.DESCRIPTION;
import static com.example.sub4.Database.DatabaseContract.FavoriteColoums.ID;
import static com.example.sub4.Database.DatabaseContract.FavoriteColoums.POSTER;
import static com.example.sub4.Database.DatabaseContract.FavoriteColoums.TITLE;

public class DetailTvShow extends AppCompatActivity {

    ImageView ivPoster;
    TextView tvTitle,tvDescription;
    Button btnFavorite;
    ProgressBar progressBar;

    private boolean isFavorite = false;
    private Favorite1 favorite;
    private int position;
    private FavoriteHelper1 favoriteHelper;

    private FavoriteTvShowAdapter favoriteTvShowAdapter;

    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_DATA = "extra_data";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        ivPoster = findViewById(R.id.iv_poster_tv_show_detail);
        tvTitle = findViewById(R.id.tv_title_tv_show_detail);
        tvDescription = findViewById(R.id.tv_description_tv_show_detail);
        btnFavorite = findViewById(R.id.btn_favorite_tv_show_detail);
        progressBar = findViewById(R.id.pb_tv_show_detail);

        showLoading(true);

        //inisialisasi untuk menambahkan ke favorite
        favoriteHelper= FavoriteHelper1.getInstance(getApplicationContext());
        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);
        favoriteTvShowAdapter = new FavoriteTvShowAdapter();
        favoriteHelper.open1();

        final TvShowModel tvShow = getIntent().getParcelableExtra(EXTRA_DATA);

        if (favorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isFavorite = true;
        } else {
            favorite = new Favorite1();
        }

        String actionBarTitle;
        String btnTitle;

        //indikator telah di pencet favorite
//        if (isFavorite){
//            actionBarTitle = "Unfavorite";
//            btnTitle = "Unfavorite";
//        }else {
//            actionBarTitle = "Favorite";
//            btnTitle = "Favorite";
//        }
//
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setTitle(actionBarTitle);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        btnFavorite.setText(btnTitle);

        if (tvShow!=null){
            final int id = tvShow.getId();
            final String poster = tvShow.getPoster();
            final String title = tvShow.getTitle();
            final String description = tvShow.getDescription();

            Glide.with(this).load(poster).fitCenter().into(ivPoster);
            tvTitle.setText(title);
            tvDescription.setText(description);

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailTvShow.this,"Favorite : "
                            +tvShow.getTitle(),Toast.LENGTH_SHORT).show();
                    favorite.setId(id);
                    favorite.setPoster(poster);
                    favorite.setTitle(title);
                    favorite.setDescription(description);

                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_FAVORITE, favorite);
                    intent.putExtra(EXTRA_POSITION, position);

                    ContentValues values = new ContentValues();
                    values.put(ID,id);
                    values.put(POSTER,poster);
                    values.put(TITLE,title);
                    values.put(DESCRIPTION, description);

                    if (isFavorite){
                        long result = favoriteHelper.update1(String.valueOf(favorite.getId()), values);
                        if (result>0){
                            setResult(RESULT_UPDATE,intent);
                            finish();
                        }
                    }else {

                        long result = favoriteHelper.insert1(values);

                        if (result>0){
                            finish();
                        }else {
                            Toast.makeText(DetailTvShow.this,"Failed to add favorite ",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            showLoading(false);
        }
    }

    private void showLoading(boolean state) {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }

    }
}
