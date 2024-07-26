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
                Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
                if (launchIntent != null) {
//                    startActivity(launchIntent);
                    Toast.makeText(this, "App unlocked: " + packageName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Unable to unlock app: " + packageName, Toast.LENGTH_SHORT).show();
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