package com.example.projectmobile.Notification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.InboxApi;
import com.example.projectmobile.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends Fragment {

    private ListView inboxListView;
    private ArrayList<Message> messageList;
    private MessageAdapter messageAdapter;
    private Context context;

    private static final String TAG = "InboxActivity";

    public InboxActivity() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_inbox, container, false);
        context = requireContext();

        inboxListView = rootView.findViewById(R.id.inbox_list);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(context, messageList);
        inboxListView.setAdapter(messageAdapter);

        loadMessagesFromApi();

        inboxListView.setOnItemClickListener((parent, view, position, id) -> {
            Message selectedMessage = messageList.get(position);

            SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            int currentUserId = selectedMessage.getReceiverId();
            if (currentUserId == -1) {
                Toast.makeText(context, "Không tìm thấy ID người dùng", Toast.LENGTH_SHORT).show();
                return;
            }

            int otherUserId = selectedMessage.getSenderId() == currentUserId
                    ? selectedMessage.getReceiverId()
                    : selectedMessage.getSenderId();

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("sender", currentUserId);
            intent.putExtra("receiver", otherUserId);
            startActivity(intent);
        });

        return rootView;
    }

    private void loadMessagesFromApi() {
        SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            return;
        }
        InboxApi inboxApi = ApiClient.getClient().create(InboxApi.class);
        Call<MessageResponse> call = inboxApi.getMessages("Bearer " + token);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    messageList.clear();
                    if (response.body().getData() != null) {
                        messageList.addAll(response.body().getData());
                    }
                    messageAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Số tin nhắn nhận được: " + messageList.size());
                } else {
                    Toast.makeText(context, "Không thể tải tin nhắn", Toast.LENGTH_SHORT).show();
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e(TAG, "Lỗi response: " + response.code() + ", body: " + errorBody);
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi khi đọc errorBody", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Lỗi kết nối API: " + t.getMessage(), t);
            }
        });
    }
}