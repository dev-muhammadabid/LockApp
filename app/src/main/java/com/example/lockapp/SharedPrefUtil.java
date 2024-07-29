package com.example.lockapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefUtil {

    private static final String SHARED_APP_PREF = "shared_app_pref";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SharedPrefUtil(Context context) {
        this.pref = context.getSharedPreferences(SHARED_APP_PREF, Context.MODE_PRIVATE);
    }

    public static SharedPrefUtil getInstance(Context context) {
        return new SharedPrefUtil(context);
    }

    public void putString(String key, String value) {
        editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return pref.getString(key, "");
    }

    public void putInteger(String key, int value) {
        editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInteger(String key) {
        return pref.getInt(key, 0);
    }

    public void putBoolean(String key, boolean value) {
        editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public void setList(String key, List<String> list) {
        editor = pref.edit();
        editor.putInt(key + "_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            editor.putString(key + "_" + i, list.get(i));
        }

        editor.apply();
    }

    public List<String> getList(String key) {
        List<String> list = new ArrayList<>();
        int size = pref.getInt(key + "_size", 0);

        for (int i = 0; i < size; i++) {
            String item = pref.getString(key + "_" + i, null);
            if (item != null) {
                list.add(item);
            }
        }

        return list;
    }
}