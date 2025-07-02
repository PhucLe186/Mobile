package com.example.projectmobile.Notification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.InboxApi;
import com.example.projectmobile.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private ListView listViewMessages;
    private EditText editTextMessage;
    private ImageView buttonSend;
    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageList;

    private int senderId;
    private int receiverId;
    private String token;

    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listViewMessages = findViewById(R.id.listViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList);
        listViewMessages.setAdapter(messageAdapter);

        receiverId = getIntent().getIntExtra("receiver", -1);
        if (receiverId == -1) {
            Toast.makeText(this, "Thiếu thông tin người nhận", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Lấy token và senderId từ SharedPreferences (giả sử bạn lưu senderId riêng hoặc giải mã token)
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = prefs.getString("token", null);
        senderId = prefs.getInt("user_id", -1);  // Giả sử bạn lưu user_id ở đây

        if (token == null || token.isEmpty() || senderId == -1) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadMessages();

        buttonSend.setOnClickListener(v -> {
            String content = editTextMessage.getText().toString().trim();
            if (!content.isEmpty()) {
                sendMessage(receiverId, content);
            } else {
                Toast.makeText(this, "Vui lòng nhập tin nhắn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMessages() {
        InboxApi inboxApi = ApiClient.getClient().create(InboxApi.class);
        Call<MessageResponse> call = inboxApi.getMessages("Bearer " + token);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse body = response.body();
                    if (body != null && body.isSuccess() && body.getData() != null) {
                        messageList.clear();
                        for (Message msg : body.getData()) {
                            // Lọc tin nhắn giữa sender và receiver hiện tại
                            if ((msg.getSenderId() == senderId && msg.getReceiverId() == receiverId) ||
                                    (msg.getSenderId() == receiverId && msg.getReceiverId() == senderId)) {
                                messageList.add(msg);
                            }
                        }
                        messageAdapter.notifyDataSetChanged();
                        listViewMessages.smoothScrollToPosition(messageList.size() - 1);
                        Log.d(TAG, "Loaded " + messageList.size() + " messages");
                    } else {
                        Toast.makeText(ChatActivity.this, "Không có tin nhắn nào", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChatActivity.this, "Không thể tải tin nhắn: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "API Failure: " + t.getMessage(), t);
            }
        });
    }

    private void sendMessage(int receiverId, String content) {
        InboxApi inboxApi = ApiClient.getClient().create(InboxApi.class);
        MessageRequest messageRequest = new MessageRequest(receiverId, content);
        Call<Void> call = inboxApi.sendMessage("Bearer " + token, messageRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChatActivity.this, "Gửi tin nhắn thành công", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Message sent");
                    editTextMessage.setText("");
                    loadMessages(); // Load lại để cập nhật
                } else {
                    Toast.makeText(ChatActivity.this, "Gửi thất bại: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Send message failed code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Send message error: " + t.getMessage(), t);
            }
        });
    }
}