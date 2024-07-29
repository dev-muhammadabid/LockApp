package com.example.lockapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class UnlockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String packageName = intent.getStringExtra("package_name");

        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
            if (appInfo != null) {
                // Typically, you might not need to launch the app here.
                // Removing or commenting out the launch logic.
                Toast.makeText(this, "App unlocked: " + packageName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "App not found: " + packageName, Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "App not found: " + packageName, Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}