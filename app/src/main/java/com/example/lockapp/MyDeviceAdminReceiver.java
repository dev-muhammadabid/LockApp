package com.example.lockapp;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    private static final String TAG = "MyDeviceAdminReceiver";

    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.d(TAG, "Device admin enabled");
        Toast.makeText(context, "Device admin enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.d(TAG, "Device admin disabled");
        Toast.makeText(context, "Device admin disabled", Toast.LENGTH_SHORT).show();
    }

}
