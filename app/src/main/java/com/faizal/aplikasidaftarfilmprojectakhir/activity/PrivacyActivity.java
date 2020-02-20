package com.faizal.aplikasidaftarfilmprojectakhir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.faizal.aplikasidaftarfilmprojectakhir.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
    }

    @Override
    public void onBackPressed() {
        Intent about = new Intent(PrivacyActivity.this, AboutActivity.class);
        this.finish();
        startActivity(about);
    }
}
