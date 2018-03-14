package com.example.sanov.simpledictionary.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sanov.simpledictionary.R;

/**
 * Created by sanov on 3/12/2018.
 */

public class AppPreference {

    SharedPreferences prefs;
    Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getFirstRun() {
        String key = context.getResources().getString(R.string.app_first_run);
        return prefs.getBoolean(key, true);
    }
}
