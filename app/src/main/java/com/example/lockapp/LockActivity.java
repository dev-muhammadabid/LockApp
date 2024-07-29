package com.example.lockapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LockActivity extends Activity {

    private static final int REQUEST_CODE_UNLOCK = 2;
    private String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_item);

        Intent intent = getIntent();
        packageName = intent.getStringExtra("package_name");

        if (packageName == null) {
            Toast.makeText(this, "Package name is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        lockApp(packageName);
    }

    private void lockApp(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
            if (appInfo != null) {
                Log.d("LockActivity", "App found: " + packageName);
                showLockScreen();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("LockActivity", "App not found exception: " + e.getMessage());
            Toast.makeText(this, "App not found: " + packageName, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showLockScreen() {
        Intent intent = new Intent(this, LockScreenActivity.class);
        intent.putExtra("package_name", packageName);
        startActivityForResult(intent, REQUEST_CODE_UNLOCK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UNLOCK) {
            if (resultCode == RESULT_OK) {
                launchApp();
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    private void launchApp() {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Unable to launch app: " + packageName, Toast.LENGTH_SHORT).show();
        }
    }
}