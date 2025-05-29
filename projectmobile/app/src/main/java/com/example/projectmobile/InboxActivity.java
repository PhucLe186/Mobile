package com.example.projectmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {

    ListView inboxListView;
    ArrayList<Message> messageList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        inboxListView = findViewById(R.id.inbox_list);

        // Tạo danh sách các Message thay vì String
        messageList = new ArrayList<>();
        messageList.add(new Message("ebe", "Đã gửi 5 giờ trước"));
        messageList.add(new Message("Tuanh", "Đã gửi 6 giờ trước"));
        messageList.add(new Message("gia đình", "đã chia sẻ một video"));
        messageList.add(new Message("em guột", "đã chia sẻ một video"));
        messageList.add(new Message("phantom voice", "đã chia sẻ một video"));

        // Adapter mới dùng class Message
        messageAdapter = new MessageAdapter(this, messageList);
        inboxListView.setAdapter(messageAdapter);

        // Khi click item thì truyền tên người gửi sang ChatActivity
        inboxListView.setOnItemClickListener((parent, view, position, id) -> {
            Message selectedMessage = messageList.get(position);
            Intent intent = new Intent(InboxActivity.this, ChatActivity.class);
            intent.putExtra("sender", selectedMessage.getSender());
            startActivity(intent);
        });
    }
}
