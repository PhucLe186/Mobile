package com.example.projectmobile.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectmobile.R;

import java.util.ArrayList;

public class InboxActivity extends Fragment {

    ListView inboxListView;
    ArrayList<Message> messageList;
    MessageAdapter messageAdapter;

    public InboxActivity() {
        // Constructor mặc định
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_inbox, container, false);

        inboxListView = rootView.findViewById(R.id.inbox_list);

        messageList = new ArrayList<>();
        messageList.add(new Message("ebe", "Đã gửi 5 giờ trước"));
        messageList.add(new Message("Tuanh", "Đã gửi 6 giờ trước"));
        messageList.add(new Message("gia đình", "đã chia sẻ một video"));
        messageList.add(new Message("em guột", "đã chia sẻ một video"));
        messageList.add(new Message("phantom voice", "đã chia sẻ một video"));

        messageAdapter = new MessageAdapter(getContext(), messageList);
        inboxListView.setAdapter(messageAdapter);

        inboxListView.setOnItemClickListener((parent, view, position, id) -> {
            Message selectedMessage = messageList.get(position);
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("sender", selectedMessage.getSender());
            startActivity(intent);
        });

        return rootView;
    }
}
