package com.example.projectmobile.Notification;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmobile.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ListView listViewMessages;
    private EditText editTextMessage;
    private ImageView buttonSend;
    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listViewMessages = findViewById(R.id.listViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        messageList = new ArrayList<>();
        // Lấy tên người gửi từ Intent
        String selected = getIntent().getStringExtra("message");
        String sender = "Không rõ";
        if (selected != null && selected.contains(":")) {
            String[] parts = selected.split(":", 2);
            sender = parts[0].trim();
        }

        // Tin nhắn mẫu ban đầu
        messageList.add(new Message(sender, "Xin chào!"));

        messageAdapter = new MessageAdapter(this, messageList);
        listViewMessages.setAdapter(messageAdapter);

        // Xử lý gửi tin nhắn
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editTextMessage.getText().toString().trim();
                if (!content.isEmpty()) {
                    messageList.add(new Message("Bạn", content));
                    messageAdapter.notifyDataSetChanged();
                    editTextMessage.setText("");
                    listViewMessages.smoothScrollToPosition(messageList.size() - 1);
                }
            }
        });
    }
}
