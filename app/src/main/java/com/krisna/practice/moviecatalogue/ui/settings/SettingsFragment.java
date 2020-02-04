package com.krisna.practice.moviecatalogue.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.utils.AlarmReceiver;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private CheckBoxPreference dailyReminderPreference;
    private CheckBoxPreference releaseTodayPreference;
    private AlarmReceiver alarmReceiver;

    private void init() {
        dailyReminderPreference = findPreference("daily_reminder");
        releaseTodayPreference = findPreference("release_today_reminder");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.setting_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_setting) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_pref, rootKey);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        alarmReceiver = new AlarmReceiver();

        if (key.equals("daily_reminder")) {
            dailyReminderPreference.setChecked(sharedPreferences.getBoolean("daily_reminder", false));
            Boolean b = sharedPreferences.getBoolean("daily_reminder", false);
            if (b) {
                alarmReceiver.setRepeatingAlarm(getContext(), alarmReceiver.TYPE_DAILY_REMINDER, alarmReceiver.DAILY_REMINDER_TIME, getContext().getResources().getString(R.string.daily_reminder_message));
            } else {
                alarmReceiver.cancelAlarm(getContext(), alarmReceiver.TYPE_DAILY_REMINDER);
            }
        }

        if (key.equals("release_today_reminder")) {
            releaseTodayPreference.setChecked(sharedPreferences.getBoolean("release_today_reminder", false));
            Boolean b = sharedPreferences.getBoolean("release_today_reminder", false);
            if (b) {
                alarmReceiver.setRepeatingAlarm(getContext(), alarmReceiver.TYPE_NEW_RELEASE_REMINDER, alarmReceiver.NEW_RELEASE_REMINDER_TIME, getContext().getResources().getString(R.string.new_release_reminder_message));
            } else {
                alarmReceiver.cancelAlarm(getContext(), alarmReceiver.TYPE_NEW_RELEASE_REMINDER);
            }
        }
    }
}
