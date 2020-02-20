package com.faizal.aplikasidaftarfilmprojectakhir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.faizal.aplikasidaftarfilmprojectakhir.R;

public class AboutActivity extends AppCompatActivity {

    Button btn_privacypolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btn_privacypolicy = findViewById(R.id.btn_privacypolicy);
        btn_privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent privacy = new Intent(AboutActivity.this, PrivacyActivity.class);
                AboutActivity.this.finish();
                startActivity(privacy);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent about = new Intent(AboutActivity.this, MainActivity.class);
        this.finish();
        startActivity(about);
    }
}
