package com.example.sub4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.sub4.Model.MoviesModel;

import static com.example.sub4.Database.DatabaseContract.FavoriteColoums.DESCRIPTION;
import static com.example.sub4.Database.DatabaseContract.FavoriteColoums.ID;
import static com.example.sub4.Database.DatabaseContract.FavoriteColoums.POSTER;
import static com.example.sub4.Database.DatabaseContract.FavoriteColoums.TITLE;

public class DetailMovies extends AppCompatActivity {


    private ImageView ivPoster;
    private TextView tvTitle, tvDescription;
    private ProgressBar progressBar;
    private Button btnFavorite;

    private boolean isFavorite = false;
    private Favorite favorite;
    private int position;
    private FavoriteHelper favoriteHelper;

    private FavoriteMoviesAdapater favoriteMoviesAdapater;

    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_DATA = "extra_data";
    public static final int RESULT_UPDATE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        ivPoster = findViewById(R.id.iv_poster_movies_detail);
        tvTitle = findViewById(R.id.tv_title_movies_detail);
        tvDescription = findViewById(R.id.tv_description_movies_detail);
        btnFavorite = findViewById(R.id.btn_favorite_movies_detail);
        progressBar = findViewById(R.id.pb_movies_detail);

        showLoading(true);

        //inisialisasi untuk menambahkan ke favorite
        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);
        favoriteMoviesAdapater = new FavoriteMoviesAdapater(this);
        favoriteHelper.open();

        //inisilisasi data dari recycler view
        final MoviesModel movies = getIntent().getParcelableExtra(EXTRA_DATA);

        if (favorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isFavorite = true;
        } else {
            favorite = new Favorite();
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

        if (movies!=null){

            final int id = movies.getId();
            final String poster = movies.getPoster();
            final String title = movies.getTitle();
            final String description = movies.getDescription();

            Glide.with(this).load(poster).fitCenter().into(ivPoster);
            tvTitle.setText(title);
            tvDescription.setText(description);

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                    Toast.makeText(DetailMovies.this,"Favorite : "
                            +movies.getTitle(),Toast.LENGTH_SHORT).show();

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
                        long result = favoriteHelper.update(String.valueOf(favorite.getId()), values);
                        if (result>0){
                            setResult(RESULT_UPDATE,intent);
                            finish();
                        }
                    }else {

                        long result = favoriteHelper.insert(values);

                        if (result>0){
                            finish();
                        }else {
                            Toast.makeText(DetailMovies.this,"Failed to add favorite",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            showLoading(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
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

