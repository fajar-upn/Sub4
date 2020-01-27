package com.example.sub4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sub4.Model.TvShowModel;

public class DetailTvShow extends AppCompatActivity {

    ImageView ivPoster;
    TextView tvTitle,tvDescription;
    Button btnFavorite;
    ProgressBar progressBar;

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

        final TvShowModel tvShow = getIntent().getParcelableExtra("data");
        if (tvShow!=null){
            String poster = tvShow.getPoster();
            String title = tvShow.getTitle();
            String description = tvShow.getDescription();

            Glide.with(this).load(poster).into(ivPoster);
            tvTitle.setText(title);
            tvDescription.setText(description);

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailTvShow.this,"Favorite "
                            +tvShow.getTitle(),Toast.LENGTH_SHORT).show();
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
