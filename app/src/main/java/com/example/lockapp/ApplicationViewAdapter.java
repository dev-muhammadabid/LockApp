package com.example.lockapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ApplicationViewAdapter extends RecyclerView.Adapter<ApplicationViewAdapter.ViewHolder> {

    private final List<AppItem> appItemList;
    private final Context context;
    private List<String> lockedApps;

    public ApplicationViewAdapter(List<AppItem> appItemList, Context context) {
        this.appItemList = appItemList;
        this.context = context;
        this.lockedApps = SharedPrefUtil.getInstance(context).getList("lockedApps");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppItem item = appItemList.get(position);
        holder.appLogo.setImageDrawable(item.getImageDrawable());
        holder.appTitle.setText(item.getTitle());
        holder.appStatus.setChecked(item.getStatus().equals("Locked"));

        holder.appStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                item.setStatus("Locked");
                lockedApps.add(item.getPackageName());
                SharedPrefUtil.getInstance(context).setList("lockedApps", lockedApps);
            } else {
                item.setStatus("Unlocked");
                lockedApps.remove(item.getPackageName());
                SharedPrefUtil.getInstance(context).setList("lockedApps", lockedApps);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appLogo;
        TextView appTitle;
        SwitchCompat appStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appLogo = itemView.findViewById(R.id.app_img);
            appTitle = itemView.findViewById(R.id.app_name);
            appStatus = itemView.findViewById(R.id.app_status);
        }
    }
}
