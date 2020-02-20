package com.faizal.aplikasidaftarfilmprojectakhir.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.notifications.NotifReceiver;
import com.faizal.aplikasidaftarfilmprojectakhir.pager.PagerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    //Ad
    AdView adBanner;
    AdRequest adRequest;
    InterstitialAd adIntersial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ad Banner
        adBanner = findViewById(R.id.adBanner);
//        adRequest = new AdRequest.Builder().addTestDevice("35B35B79A35DC558C493A8D674367BC7").build();
        adRequest = new AdRequest.Builder().build();
        adBanner.loadAd(adRequest);

        //Ad Intersial
        createInterstitial();

        PagerAdapter sectionsPagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_films);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tab_films);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }

    public void createInterstitial() {
        adIntersial = new InterstitialAd(this);
        adIntersial.setAdUnitId(getString(R.string.ad_intersial));
        loadInterstitial();
    }

    public void loadInterstitial() {
//        AdRequest interstitialRequest = new AdRequest.Builder().addTestDevice("35B35B79A35DC558C493A8D674367BC7").build();
        AdRequest interstitialRequest = new AdRequest.Builder().build();
        adIntersial.loadAd(interstitialRequest);
    }

    public void showInterstitialSearch() {
        if (adIntersial.isLoaded()) {
            adIntersial.show();
            adIntersial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // not call show interstitial ad from here
                }

                @Override
                public void onAdClosed() {
                    loadInterstitial();

                    ////////////////////////////////
                    Intent search = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(search);
                    ////////////////////////////////
                }
            });
        } else {
            loadInterstitial();

            ////////////////////////////////
            Intent search = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(search);
            ////////////////////////////////
        }
    }

    public void showInterstitialNotif() {
        if (adIntersial.isLoaded()) {
            adIntersial.show();
            adIntersial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // not call show interstitial ad from here
                }

                @Override
                public void onAdClosed() {
                    loadInterstitial();

                    ////////////////////////////////
                    Intent notif = new Intent(MainActivity.this, NotifActivity.class);
                    startActivity(notif);
                    ////////////////////////////////
                }
            });
        } else {
            loadInterstitial();

            ////////////////////////////////
            Intent notif = new Intent(MainActivity.this, NotifActivity.class);
            startActivity(notif);
            ////////////////////////////////
        }
    }

    private void exitDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle(getString(R.string.msg_questionExit));
        alertDialogBuilder
                .setMessage(getString(R.string.msg_questionYes))
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.msg_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_language) {
            Intent lang = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(lang);
        } else if (item.getItemId() == R.id.intentSeacrh) {
            showInterstitialSearch();
        } else if (item.getItemId() == R.id.notifications) {
            showInterstitialNotif();
        } else if (item.getItemId() == R.id.intentExit) {
            exitDialog();
        } else if (item.getItemId() == R.id.intentAbout) {
            Intent about = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(about);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean doubleTapParam = false;
    @Override
    public void onBackPressed() {
        if (doubleTapParam) {
            super.onBackPressed();
            return;
        }

        this.doubleTapParam = true;
        Toast.makeText(this, getString(R.string.msgTap2x), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleTapParam = false;
            }
        }, 2000);
    }
}
