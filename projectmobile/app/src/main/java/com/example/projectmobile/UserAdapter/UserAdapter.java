package com.example.projectmobile.UserAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.projectmobile.R;
import com.example.projectmobile.model.User;
import java.text.BreakIterator;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public interface OnFollowClickListener {
        void onFollowClick(int position, User user);
    }

    private final List<User> userList;
    private final Context context;
    private final OnFollowClickListener listener;

    public UserAdapter(List<User> userList, Context context, OnFollowClickListener listener) {
        this.userList = userList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iterm_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        // Set user info
        holder.username.setText(user.getUsername());

        // Load avatar with Glide
        Glide.with(context)
                .load(user.getAvatarUrl())
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .circleCrop()
                .into(holder.avatar);

        // Update follow button
        updateFollowButton(holder.followButton, user.isFollowing());

        // Set click listener
        holder.followButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onFollowClick(adapterPosition, userList.get(adapterPosition));
            }
        });
    }

    private void updateFollowButton(Button button, boolean isFollowing) {
        if (isFollowing) {
            button.setText(R.string.following);
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.gray_light));
        } else {
            button.setText(R.string.follow);
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red_tiktok));
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    protected static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username;
        Button followButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            username = itemView.findViewById(R.id.username);
            followButton = itemView.findViewById(R.id.followButton);
        }
    }
}
