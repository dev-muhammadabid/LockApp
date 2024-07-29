package com.example.lockapp;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.pm.ApplicationInfo;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.provider.Settings;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button start_btn = findViewById(R.id.start_btn);
        start_btn.setOnClickListener(v -> {
            //Navigation MainActivity To App View
            Intent intent = new Intent(MainActivity.this, ApplicationViewActivity.class);
            startActivity(intent);
            //Take Permission To User
            if(!isAccessGranted()) {
                Intent usageAccess = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(usageAccess);
            }
        });
    }

    //Function to check the Access is Granted or not.
    private boolean isAccessGranted(){
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}