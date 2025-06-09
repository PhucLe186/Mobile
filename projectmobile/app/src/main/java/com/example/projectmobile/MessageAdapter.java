package com.example.projectmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        senderName.setText(message.getSender());
        messageText.setText(message.getContent());
        avatar.setImageResource(R.drawable.user);
        actionIcon.setImageResource(R.drawable.ic_camera);

        return convertView;
    }
}
