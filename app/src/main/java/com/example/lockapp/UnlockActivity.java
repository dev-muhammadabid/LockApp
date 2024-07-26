package com.example.lockapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class UnlockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String packageName = intent.getStringExtra("package_name");
        // Add logic to unlock the app with the given package name
        Toast.makeText(this, "App unlocked: " + packageName, Toast.LENGTH_SHORT).show();
        finish();
    }
}