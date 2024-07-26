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

import java.util.List;

public class ApplicationViewAdapter extends RecyclerView.Adapter<ApplicationViewAdapter.ViewHolder> {

    private final List<AppItem> appItemList;
    private final Context context;

    public ApplicationViewAdapter(List<AppItem> appItemList, Context context) {
        this.appItemList = appItemList;
        this.context = context;
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
                // Lock the app
                Intent lockIntent = new Intent(context, LockActivity.class);
                lockIntent.putExtra("package_name", item.getPackageName());
                context.startActivity(lockIntent);
            } else {
                item.setStatus("Unlocked");
                // Unlock the app
                Intent unlockIntent = new Intent(context, UnlockActivity.class);
                unlockIntent.putExtra("package_name", item.getPackageName());
                context.startActivity(unlockIntent);
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