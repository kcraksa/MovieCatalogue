package com.krisna.practice.moviecatalogue.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.krisna.practice.moviecatalogue.MainActivity;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.MovieList;
import com.krisna.practice.moviecatalogue.services.api.ApiClient;
import com.krisna.practice.moviecatalogue.services.api.GetMovieDataServices;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TIME_FORMAT = "HH:mm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TITLE = "Movie Catalogue Reminder";
    public static final String EXTRA_RELEASED_TODAY_TITLE = "New Release!";
    public static final String DAILY_REMINDER_TIME = "07:00";
    public static final String NEW_RELEASE_REMINDER_TIME = "08:00";
    public static final String TYPE_DAILY_REMINDER = "DailyReminder";
    public static final String TYPE_NEW_RELEASE_REMINDER = "NewReleaseReminder";
    public static final String EXTRA_TYPE = "type";

    private static final String API_KEY = "49c4a18c1b3a930bda11d8630c987291";
    private String current_language = Locale.getDefault().getLanguage();
    private String current_country = Locale.getDefault().getCountry();
    public String numberMovieRelease;

    private final int ID_DAILY_REMINDER = 101;
    private final int ID_RELEASED_TODAY_REMINDER = 100;

    private GetMovieDataServices getMovieDataServices;

    public AlarmReceiver() {
        getMovieDataServices = ApiClient.createService(GetMovieDataServices.class);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String type = intent.getStringExtra(EXTRA_TYPE);

        final String title = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? EXTRA_TITLE : EXTRA_RELEASED_TODAY_TITLE;
        final int notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASED_TODAY_REMINDER;

        if (current_language.equals("in")) {
            current_language = "id"; // untuk menyamakan kode bahasa indonesia sistem dengan data moviedb
        }

        String language = current_language + "-" + current_country;

        if (type.equalsIgnoreCase(TYPE_NEW_RELEASE_REMINDER)) {

            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            getMovieReleaseToday(API_KEY, language, currentDate, currentDate, new NewReleaseCallback() {
                @Override
                public void onSuccess(String numberOfMovie) {

                    String msg = context.getResources().getString(R.string.new_release_reminder_message);
                    msg = msg.replace("xxx", numberOfMovie);

                    numberMovieRelease = numberOfMovie;
                    showAlarmNotification(context, title, msg, notifId);
                }

                @Override
                public void onFailed() {
                    numberMovieRelease = "0";
                }
            });
        } else {
            showAlarmNotification(context, title, message, notifId);
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager Channel";

        Intent notificationIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            Notification notification = builder.build();

            if (notificationManager != null) {
                notificationManager.notify(notifId, notification);
            }
        }
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        int notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASED_TODAY_REMINDER;

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        String toastMessage = context.getResources().getString(R.string.enable_daily_reminder).replace("xxx", time);

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASED_TODAY_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        String toastMessage;
        if (type.equalsIgnoreCase(TYPE_DAILY_REMINDER)) {
            toastMessage = context.getResources().getString(R.string.cancel_daily_reminder);
        } else {
            toastMessage = context.getResources().getString(R.string.cancel_new_release_reminder);
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();

    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());

            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void getMovieReleaseToday(String api_key, String language, String fromDate, String toDate, final NewReleaseCallback callback) {

        Log.d("TEST_2", fromDate+"~"+toDate);
        getMovieDataServices.getMovieReleaseToday(api_key, language, fromDate, toDate).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                callback.onSuccess(response.body().getTotal_results());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                callback.onFailed();
            }
        });
    }

    public interface NewReleaseCallback {
        void onSuccess(String numberOfMovie);
        void onFailed();
    }
}
