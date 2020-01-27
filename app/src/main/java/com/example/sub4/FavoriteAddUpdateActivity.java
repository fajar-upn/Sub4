package com.example.sub4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sub4.Database.FavoriteHelper;
import com.example.sub4.Entity.Favorite;

public class FavoriteAddUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ivPoster;
    private TextView tvTitle, tvDescription;
    private ProgressBar progressBar;
    private Button btnUnfavorite;

    private boolean isEdit = false;
    private Favorite favorite;
    private int position;
    private FavoriteHelper favoriteHelper;

//    public static final String EXTRA_FAVORITE = "extra_favorite";
//    public static final String EXTRA_POSITION = "extra_position";
//    public static final int REQUEST_ADD = 100;
//    public static final int RESULT_ADD = 101;
//    public static final int REQUEST_UPDATE = 200;
//    public static final int RESULT_UPDATE = 201;
//    public static final int RESULT_DELETE = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_add_update);

        ivPoster = findViewById(R.id.iv_poster_favorite_detail);
        tvTitle = findViewById(R.id.tv_title_favorite_detail);
        tvDescription = findViewById(R.id.tv_description_favorite_detail);
        btnUnfavorite = findViewById(R.id.btn_favorite_unfavorite_detail);
        progressBar = findViewById(R.id.pb_favorite_detail);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
//
//        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);
//        if (favorite!=null){
//            position = getIntent().getIntExtra(EXTRA_POSITION,0);
//            isEdit = true;
//        }else {
//            favorite = new Favorite();
//        }

//        String actionBarTitle;
//        String btnTitle;
//
//        if (isEdit){
//            actionBarTitle = "Ubah";
//            btnTitle = "Update";
//
//            if (favorite!=null){
//
//            }
//        }else {
//            actionBarTitle = "Tambah";
//            btnTitle="Simpan";
//        }

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(actionBarTitle);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        btnSubmit.setText(btnTitle);



        showLoading(true);

        btnUnfavorite.setOnClickListener(this);





//        final Favorite favorite = getIntent().getParcelableExtra("data");
//        if (favorite!=null){
//            String poster = favorite.getPoster();
//            String title = favorite.getTitle();
//            String description = favorite.getDescription();
//
//            Glide.with(this).load(poster).into(ivPoster);
//            tvTitle.setText(title);
//            tvDescription.setText(description);
//
//            btnFavorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v){
//                    Toast.makeText(FavoriteAddUpdateActivity.this,"Favorite"
//                            +favorite.getTitle(),Toast.LENGTH_SHORT).show();
//
//                }
//            });
            showLoading(false);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_favorite_unfavorite_detail:{

            }
            break;
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
