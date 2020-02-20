package com.faizal.aplikasidaftarfilmprojectakhir.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.activity.MainActivity;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class NotifReceiver extends BroadcastReceiver {

    public static final String DAILY_REMINDER = "DailyReminder";
    public static final String RELEASE_REMINDER = "ReleaseTodayReminder";
    public static final String EXTRAS = "type";

    private final int DAILY_ID = 100;
    private final int RELEASE_ID = 101;

    private Context context;

    private static final String API_KEY = "022df932950ade918fbf7c740d31e834";
    private MutableLiveData<ArrayList<Film>> listFilm = new MutableLiveData<>();

    public NotifReceiver() {
    }

    public NotifReceiver(Context context) {
        this.context = context;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRAS);

        if (type.equals(DAILY_REMINDER)) {
            showDailyReminder(context);
        } else if (type.equals(RELEASE_REMINDER)) {
            getReleaseToday(context);
        }
    }

    private void showDailyReminder(Context context) {
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Daily Reminder channel";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_local_movies))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.message_daily_reminder))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = mBuilder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void showReleaseToday(Context context, String title, String desc, int id) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Today release channel";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_local_movies))
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = mBuilder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        if (mNotificationManager != null) {
            mNotificationManager.notify(id, notification);
        }
    }

    private void getReleaseToday(final Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String date_now = dateFormat.format(date);

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY +"&primary_release_date.gte="+date_now + "&primary_release_date.lte=" + date_now ;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {

                        JSONObject jObj = list.getJSONObject(i);

                        String title = jObj.getString("title");
                        String desc = title+" "+context.getString(R.string.message_release_reminder);

                        showReleaseToday(context, title, desc, i);
                    }

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setDailyReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_ID, getReminderIntent(DAILY_REMINDER), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTimeDaily().getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public void setReleaseTodayReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RELEASE_ID, getReminderIntent(RELEASE_REMINDER), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTimeRelease().getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotifReceiver.class);

        int requestCode = type.equalsIgnoreCase(DAILY_REMINDER) ? DAILY_ID : RELEASE_ID;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void cancelDailyReminder(Context context) {
        cancelReminder(context, DAILY_REMINDER);
    }

    public void cancelReleaseToday(Context context) {
        cancelReminder(context, RELEASE_REMINDER);
    }

    private Intent getReminderIntent(String notif) {
        Intent intent = new Intent(context, NotifReceiver.class);
        intent.putExtra(EXTRAS, notif);

        return intent;
    }

    private Calendar getReminderTimeDaily() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 7 );
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        if (cal.before(Calendar.getInstance())) {
            cal.add(Calendar.DATE, 1);
        }

        return cal;
    }

    private Calendar getReminderTimeRelease() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        if (cal.before(Calendar.getInstance())) {
            cal.add(Calendar.DATE, 1);
        }

        return cal;
    }
}
