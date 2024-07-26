package com.example.lockapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class LockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String packageName = intent.getStringExtra("package_name");

        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
            if (appInfo != null) {
                Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
                if (launchIntent != null) {
                    ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                    activityManager.killBackgroundProcesses(packageName);
                    Toast.makeText(this, "App locked: " + packageName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Unable to lock app: " + packageName, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "App not found: " + packageName, Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "App not found: " + packageName, Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}