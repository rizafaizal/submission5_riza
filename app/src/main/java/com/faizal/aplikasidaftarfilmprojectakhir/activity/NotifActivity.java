package com.faizal.aplikasidaftarfilmprojectakhir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.notifications.NotifReceiver;

public class NotifActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEdit;
    private NotifReceiver notifReceiver;
    private static final String PREFS_NAME = "user_pref";

    SwitchCompat swtch_release, swtch_daily ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        swtch_daily = findViewById(R.id.swtch_daily);
        swtch_release = findViewById(R.id.swtch_release);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        notifReceiver = new NotifReceiver(this);

        swtchChoose();
        setPreferences();
    }

    private void swtchChoose() {
        swtch_daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferenceEdit = sharedPreferences.edit();
                sharedPreferenceEdit.putBoolean("daily_reminder", isChecked);
                sharedPreferenceEdit.apply();
                if (isChecked) {
                    notifReceiver.setDailyReminder();
                    Toast.makeText(NotifActivity.this, getString(R.string.notif_swtch_daily_on), Toast.LENGTH_SHORT).show();
                } else {
                    notifReceiver.cancelDailyReminder(getApplicationContext());
                    Toast.makeText(NotifActivity.this, getString(R.string.notif_swtch_daily_off), Toast.LENGTH_SHORT).show();
                }
            }
        });

        swtch_release.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferenceEdit = sharedPreferences.edit();
                sharedPreferenceEdit.putBoolean("release_reminder", isChecked);
                sharedPreferenceEdit.apply();
                if (isChecked) {
                    notifReceiver.setReleaseTodayReminder();
                    Toast.makeText(NotifActivity.this, getString(R.string.notif_swtch_release_on), Toast.LENGTH_SHORT).show();
                } else {
                    notifReceiver.cancelReleaseToday(getApplicationContext());
                    Toast.makeText(NotifActivity.this, getString(R.string.notif_swtch_release_off), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setPreferences() {
        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);
        boolean releaseReminder = sharedPreferences.getBoolean("release_reminder", false);
        swtch_daily.setChecked(dailyReminder);
        swtch_release.setChecked(releaseReminder);
    }
}
