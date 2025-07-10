package com.example.projectmobile.Notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.InboxApi;
import com.example.projectmobile.Notification.model.MessageList;
import com.example.projectmobile.R;
import com.example.projectmobile.SocketConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private Socket socket;
    private TextView name, namee;
    private ChatAdapter adapter;
    private InboxApi inboxApi;
    private ListView listViewMessages;
    private EditText editTextMessage;
    private ImageView buttonSend, avata, avata2, back;
    private List<MessageList> messageList;

    private int Newid;
    private String token, avt, Name;

    private static final String TAG = "ChatActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();
        setupListeners();
        getIntentData();
        initChat();
    }
    private void initChat() {
        if (Newid == -1) {
            Toast.makeText(this, "Thiếu thông tin người nhận", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        inboxApi = ApiClient.getClient().create(InboxApi.class);
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(this, messageList); // Khởi tạo adapter trước
        listViewMessages.setAdapter(adapter);
        loadMessages();

        Glide.with(this).load(avt).circleCrop().into(avata);
        Glide.with(this).load(avt).circleCrop().into(avata2);
        name.setText(Name);
        namee.setText(Name);
//        socket = SocketConfig.getSocket(token);
        conectSocket();
    }
    private void getIntentData() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        token = "Bearer " + prefs.getString("token", "");
        Newid = getIntent().getIntExtra("id", -1);
        avt = getIntent().getStringExtra("avt");
        Name = getIntent().getStringExtra("name");
    }
    private void conectSocket() {
        socket = SocketConfig.getSocket(token);
        if (socket.connected()) {
            socket.emit("room", Newid);
            return;
        }
        socket.connect();
        socket.on(Socket.EVENT_CONNECT, args -> {
            try {
                JSONObject joinData = new JSONObject();
                joinData.put("peer_id", Newid);
                socket.emit("room", joinData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        socket.on("receive", args -> {
            runOnUiThread(()->{
                try{
                    JSONObject msg=(JSONObject) args[0];
                    int sender_id=msg.getInt("sender_id");
                    int receive_id=msg.getInt("receiver_id");
                    String messageContent = msg.getString("message");

                    if(receive_id == Newid){
                        return ;
                    }
                    else {
                        MessageList message = new MessageList();
                        message.setMyself(0);
                        message.setMessage(messageContent);
                        message.setSender_avatar(avt);
                        messageList.add(message);
                        adapter.notifyDataSetChanged();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            });
        });
    }
    private void setupListeners() {
        back.setOnClickListener(v -> finish());
        buttonSend.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();
            if(!message.isEmpty()){
                try {
                    MessageList newMessage = new MessageList();
                    newMessage.setMessage(message);
                    newMessage.setMyself(1);
                    messageList.add(newMessage);
                    adapter.notifyDataSetChanged();
                    JSONObject msg = new JSONObject();
                    msg.put("receiver_id", Newid);
                    msg.put("message",message);
                    socket.emit("send",msg);
                    editTextMessage.setText("");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initViews() {
        listViewMessages = findViewById(R.id.listViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        avata = findViewById(R.id.avt);
        avata2 = findViewById(R.id.avt2);
        name = findViewById(R.id.name);
        namee = findViewById(R.id.name2);
        back = findViewById(R.id.btnback);
    }
    private void loadMessages() {
        inboxApi.fetchChatMessages(token, Newid).enqueue(new Callback<List<MessageList>>() {
            @Override
            public void onResponse(Call<List<MessageList>> call, Response<List<MessageList>> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    messageList.clear(); // Xóa danh sách cũ
                    messageList.addAll(response.body()); // Thêm tin nhắn từ API
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<MessageList>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "lỗi lấy tin: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            socket.off("receive");
            socket.disconnect();
            SocketConfig.disconnectSocket();
        }
    }
}