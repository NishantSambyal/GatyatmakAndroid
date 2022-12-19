package com.gatyatmakjyotish.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.ui.activity.ChangeFontSize;

import static com.gatyatmakjyotish.constants.Constants.TEXT_SIZE;

public class SaveTextSize {
    private static SaveTextSize ourInstance;
    private SharedPreferences sharedPreferences;


    private SaveTextSize(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.TEXT_PREF, Context.MODE_PRIVATE);
    }

    public static SaveTextSize getInstance(Context context) {
        if (ourInstance == null) {
            return ourInstance = new SaveTextSize(context);
        }
        return ourInstance;
    }

    public void saveTextSize(int textSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TEXT_SIZE, textSize);
        editor.apply();
    }

    public int getTextSize() {
        return sharedPreferences.getInt(TEXT_SIZE, ChangeFontSize.DEVICE);
    }
}
