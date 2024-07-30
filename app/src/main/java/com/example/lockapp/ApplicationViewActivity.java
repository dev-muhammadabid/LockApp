package com.example.lockapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ApplicationViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<AppItem> appItemList = new ArrayList<>();
    ApplicationViewAdapter adapter;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_view);

        context = this;
        recyclerView = findViewById(R.id.app_list);
        adapter = new ApplicationViewAdapter(appItemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Apps");
        progressDialog.setMessage("Loading");
        progressDialog.setOnShowListener(dialog -> getInstalledApps());
        progressDialog.show();
    }

    private void getInstalledApps() {
        List<String> lockedAppsList = SharedPrefUtil.getInstance(context).getList("lockedApps");

        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        appItemList.clear();
        for (PackageInfo packageInfo : packageInfos) {
            Intent intent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
            if (intent != null) {
                String packageName = packageInfo.packageName;
                String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                Drawable appIcon = packageInfo.applicationInfo.loadIcon(packageManager);

                boolean isLocked = lockedAppsList.contains(packageName);
                String status = isLocked ? "Locked" : "Unlocked";

                AppItem appItem = new AppItem(appIcon, appName, packageName, status);
                appItemList.add(appItem);
            }
        }

        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }
}
