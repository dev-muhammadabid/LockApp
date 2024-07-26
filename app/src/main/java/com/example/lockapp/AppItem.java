package com.example.lockapp;

import android.graphics.drawable.Drawable;

public class AppItem {
    private Drawable imageDrawable;
    private String title;
    private String packageName;
    private String status;

    public AppItem(Drawable imageDrawable, String title, String packageName, String status) {
        this.imageDrawable = imageDrawable;
        this.title = title;
        this.packageName = packageName;
        this.status = status;
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public String getTitle() {
        return title;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}