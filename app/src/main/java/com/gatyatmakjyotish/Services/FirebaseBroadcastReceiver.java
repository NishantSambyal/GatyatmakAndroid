package com.gatyatmakjyotish.Services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.legacy.content.WakefulBroadcastReceiver;

import static android.content.Context.MODE_PRIVATE;
import static com.gatyatmakjyotish.constants.Constants.NOTIFICATION_PREF;

/**
 * Created by hp on 4/14/2018.
 */

public class FirebaseBroadcastReceiver extends WakefulBroadcastReceiver {

    String CHANNEL_ID = "channel5";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("wyuweyueyueyueyueyueyuyu");
        Bundle extras = intent.getExtras();
        String title = "";
        String message = "";
        if (extras != null) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                if (key.equalsIgnoreCase("gcm.notification.title"))
                    title = value.toString();
                if (key.equalsIgnoreCase("gcm.notification.body"))
                    message = value.toString();
            }

            sharedPreferences = context.getSharedPreferences(NOTIFICATION_PREF, MODE_PRIVATE);
            editor = sharedPreferences.edit();

            editor.putString("title", title);
            editor.putString("message", message);
            editor.apply();
        }
    }
}