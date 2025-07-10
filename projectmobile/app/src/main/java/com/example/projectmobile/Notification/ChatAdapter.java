package com.example.projectmobile.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.Notification.model.MessageList;
import com.example.projectmobile.R;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;

    private Context context;
    private List<MessageList> messageList;

    public ChatAdapter(Context context, List<MessageList> messageList) {
        this.context = context;
        this.messageList = messageList;

    }
    @Override
    public int getCount() {
        return messageList.size();
    }
    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        MessageList message = messageList.get(position);
        return message.getMyself() == 1 ? TYPE_RIGHT : TYPE_LEFT;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        MessageList message = messageList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    viewType == TYPE_RIGHT ? R.layout.item_message_right : R.layout.item_message_left,
                    parent,
                    false
            );
        }

        if (viewType == TYPE_RIGHT) {
            TextView messageText = convertView.findViewById(R.id.textViewMessage);
            messageText.setText(message.getMessage());
        } else {
            TextView messageText = convertView.findViewById(R.id.textViewMessage);
            ImageView avatar = convertView.findViewById(R.id.avt);

            messageText.setText(message.getMessage());
            messageText.setText(message.getMessage());

            if (viewType == TYPE_LEFT) {
                boolean isLastFromSender = true;
                if (position < messageList.size() - 1) {
                    MessageList nextMessage = messageList.get(position + 1);
                    if (nextMessage.getMyself() == 0) {
                        isLastFromSender = false;
                    }
                }
                if (isLastFromSender) {
                    avatar.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(message.getSender_avatar())
                            .circleCrop()
                            .into(avatar);
                } else {
                    avatar.setVisibility(View.INVISIBLE);
                }
            }
        }

        return convertView;
    }

}
