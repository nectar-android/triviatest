package com.example.triviagame.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;


public class Utils extends Application {

    public static Utils utils;
    private static OkHttpClient httpClient;
    // private context made because no one can use ot store in this context
    public Context context = null;
    Animation animation;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;

    private Utils() {
    }

    //To create single instance
    public static Utils getInstance() {
        if (utils == null) {
            synchronized (Utils.class) {
                if (utils == null) {
                    utils = new Utils();

                }
            }
        }
        return utils;
    }

    public void initializeObj() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public Context getCon() {
        return context;
    }


    /**
     * @param message Pass message to show user
     * @return It will return long toast message whatever you pass in your
     * application
     */
    public void Toast(String message) {
        final String onTimeMsg = message;
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, onTimeMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @return It will check your Internet connection.True if any net connected.
     */
    public boolean isNetConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    /**
     * set string Preference
     */
    public void setPreference(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * get string preferences
     */
    public String getPreference(String key) {
        return preferences.getString(key, "");
    }

    public void setIntPreference(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

}
