package com.example.projectmobile.Setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectmobile.R;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SettingItem> items;

    public SettingAdapter(Context context, ArrayList<SettingItem> items) {
        this.context = context;
        this.items = items;
    }
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.headerTitle);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            textView = itemView.findViewById(R.id.textView2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SettingItem.TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SettingItem item = items.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).headerTitle.setText(item.getTitle());
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.textView.setText(item.getTitle());
            itemHolder.imageView.setImageResource(item.getIcon());

            SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", context.MODE_PRIVATE);
            String token = prefs.getString("token", "");

            if(token.isEmpty()){
                itemHolder.itemView.setOnClickListener(v -> {
                    if (context instanceof SettingNotLogin) {
                        ((SettingNotLogin) context).onSettingItemClick(item);
                        ;
                    }
                });
            }

            else {
                itemHolder.itemView.setOnClickListener(v -> {
                    if (context instanceof SettingActivity) {
                        ((SettingActivity) context).handleSettingClick(item);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
