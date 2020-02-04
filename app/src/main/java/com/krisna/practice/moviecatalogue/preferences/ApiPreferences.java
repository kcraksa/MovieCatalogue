package com.krisna.practice.moviecatalogue.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApiPreferences {
    private static final String URL_POSTER = "https://image.tmdb.org/t/p/w185/";

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getUrlPoster(Context context) {
        return getSharedPreference(context).getString(URL_POSTER, "");
    }
}
