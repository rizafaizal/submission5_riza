package com.faizal.aplikasidaftarfilmprojectakhir.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.db.TvShowHelper;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TVsColoumns.OVERVIEW_TVS;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TVsColoumns.POSTER_TVS;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TVsColoumns.RELEASE_TVS;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TVsColoumns.SCORE_TVS;
import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.TVsColoumns.TITLE_TVS;

public class DetailActivityTVShow extends AppCompatActivity implements View.OnClickListener {

    ImageView imgFilm;
    TextView txtJudulFilm, txtReleaseFilm, txtScoreFilm, txtDescFilm;
    private ProgressBar progressBar;
    private Button btnFavorite;

    //Ad
    AdView adBanner;
    AdRequest adRequest;
    InterstitialAd adIntersial;

    public static final String EXTRA_TVs = "extra_tvs";

    TvShowHelper tvShowHelper;
    int action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        imgFilm = findViewById(R.id.img_photo_detailTV);
        txtJudulFilm = findViewById(R.id.txt_judul_detailTV);
        txtReleaseFilm = findViewById(R.id.txt_release_detailTV);
        txtScoreFilm = findViewById(R.id.txt_score_detailTV);
        txtDescFilm = findViewById(R.id.txt_desc_detail);
        progressBar = findViewById(R.id.progressBar_detailTV);
        btnFavorite = findViewById(R.id.btn_favTvs_detail);
        btnFavorite.setOnClickListener(this);

        showLoading(true);

        Film films = getIntent().getParcelableExtra(EXTRA_TVs);
        if (films != null) {
            showLoading(false);
            txtJudulFilm.setText(films.getTitle());
            txtReleaseFilm.setText(films.getRelease());
            txtScoreFilm.setText(films.getScore());
            txtDescFilm.setText(films.getOverview());
            Glide.with(this)
                    .load(films.getPoster())
                    .apply(new RequestOptions().override(950, 900))
                    .into(imgFilm);

            tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
            tvShowHelper.open();

            String tvsTitle = films.getTitle();
            Log.d("test", "onCreate: "+tvsTitle+tvShowHelper.getOne(tvsTitle));

            if ( tvShowHelper.getOne(tvsTitle)){
                //delete
                btnFavorite.setText(getString(R.string.favorite_delete));
                btnFavorite.setBackgroundColor(Color.RED);
                action = 0;
            } else if (!tvShowHelper.getOne(tvsTitle)){
                //insert
                btnFavorite.setText(getString(R.string.favorite_add));
                btnFavorite.setBackgroundColor(Color.BLUE);
                action = 1;
            }
        }

        //Ad Banner
        adBanner = findViewById(R.id.adBannerDetailTVs);
//        adRequest = new AdRequest.Builder().addTestDevice("35B35B79A35DC558C493A8D674367BC7").build();
        adRequest = new AdRequest.Builder().build();
        adBanner.loadAd(adRequest);

        //Nama Bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getResources().getString(R.string.bar_detail_film));
        ab.setSubtitle(films.getTitle());
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        Film tvs = getIntent().getParcelableExtra(EXTRA_TVs);

        if (action==1){
            ContentValues values = new ContentValues();
            values.put(TITLE_TVS, tvs.getTitle());
            values.put(RELEASE_TVS, tvs.getRelease());
            values.put(OVERVIEW_TVS, tvs.getOverview());
            values.put(SCORE_TVS, tvs.getScore());
            values.put(POSTER_TVS, tvs.getPoster());
            long result = TvShowHelper.insert(values);

            btnFavorite.setText(getString(R.string.favorite_delete));
            btnFavorite.setBackgroundColor(Color.RED);
            action = 0;

            if (result > 0) {
                Toast.makeText(DetailActivityTVShow.this, getString(R.string.favorite_success)+" "+getString(R.string.favorite_add), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(DetailActivityTVShow.this, getString(R.string.favorite_failed)+" "+getString(R.string.favorite_add), Toast.LENGTH_SHORT).show();

            }

        } else if(action == 0){
            long result = tvShowHelper.deleteByTitle(tvs.getTitle());
            if (result > 0) {
                Toast.makeText(DetailActivityTVShow.this, getString(R.string.favorite_success)+" "+getString(R.string.favorite_delete), Toast.LENGTH_SHORT).show();

                action = 1;
                btnFavorite.setText(getString(R.string.favorite_add));
                btnFavorite.setBackgroundColor(Color.BLUE);
            } else {
                Toast.makeText(DetailActivityTVShow.this, getString(R.string.favorite_failed)+" "+getString(R.string.favorite_delete), Toast.LENGTH_SHORT).show();

            }
        }
    }
}
