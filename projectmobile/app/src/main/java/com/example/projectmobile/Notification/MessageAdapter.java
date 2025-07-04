package com.example.projectmobile.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.R;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }

        ImageView avatar = convertView.findViewById(R.id.avatar);
        TextView senderName = convertView.findViewById(R.id.sender_name);
        TextView messageText = convertView.findViewById(R.id.message_text);
        ImageView actionIcon = convertView.findViewById(R.id.action_icon);

        if(message.getMyself()==1){
            senderName.setText(message.getReceiverUsername());
            Glide.with(getContext())
                    .load(message.getReceiverAvatar()).circleCrop().into(avatar);
        }
        else {
            // Nếu bạn chỉ có senderId (int), thì nên hiển thị là "Người dùng #<id>"
            senderName.setText(message.getSenderUsername());
            Glide.with(getContext())
                    .load(message.getSenderAvatar()).circleCrop().into(avatar);
        }
        // Nội dung tin nhắn
        messageText.setText(message.getMessage());
        // Avatar và icon hành động có thể giữ nguyên
        avatar.setImageResource(R.drawable.user);
        actionIcon.setImageResource(R.drawable.ic_camera);

        return convertView;
    }
}