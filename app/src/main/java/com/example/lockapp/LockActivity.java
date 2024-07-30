package com.example.lockapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class LockActivity extends Activity {

    private static final int REQUEST_CODE_UNLOCK = 2;
    private String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_item); // Ensure this layout is correct for LockActivity

        Intent intent = getIntent();
        if (intent != null) {
            packageName = intent.getStringExtra("package_name");
        }

        if (packageName == null || packageName.isEmpty()) {
            Toast.makeText(this, "Package name is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("LockActivity", "Received package name: " + packageName);
        lockApp(packageName);
    }

    private void lockApp(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
            if (appInfo != null) {
                Log.d("LockActivity", "App found: " + packageName);
                showLockScreen();
            } else {
                Log.e("LockActivity", "App not found: " + packageName);
                Toast.makeText(this, "App not found: " + packageName, Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("LockActivity", "App not found exception: " + e.getMessage());
            Toast.makeText(this, "App not found: " + packageName, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showLockScreen() {
        List<String> lockedApps = SharedPrefUtil.getInstance(this).getList("lockedApps");
        Log.d("LockActivity", "Locked apps list: " + lockedApps);

        if (lockedApps != null && lockedApps.contains(packageName)) {
            Log.d("LockActivity", "App is locked: " + packageName);
            Intent intent = new Intent(this, LockScreenActivity.class);
            intent.putExtra("package_name", packageName);
            startActivityForResult(intent, REQUEST_CODE_UNLOCK);
        } else {
            Log.d("LockActivity", "App is not locked: " + packageName);
            launchApp();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UNLOCK) {
            if (resultCode == RESULT_OK) {
                launchApp();
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void launchApp() {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            Log.d("LockActivity", "Launching app: " + packageName);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Unable to launch app: " + packageName, Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
