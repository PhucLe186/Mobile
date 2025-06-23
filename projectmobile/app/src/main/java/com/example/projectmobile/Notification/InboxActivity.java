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

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.InboxApi;
import com.example.projectmobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        messageAdapter = new MessageAdapter(getContext(), messageList);
        inboxListView.setAdapter(messageAdapter);

        // Gọi API để lấy danh sách tin nhắn
        loadMessagesFromApi();

        inboxListView.setOnItemClickListener((parent, view, position, id) -> {
            Message selectedMessage = messageList.get(position);
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("sender", selectedMessage.getSender());
            startActivity(intent);
        });

        return rootView;
    }

    private void loadMessagesFromApi() {
        InboxApi inboxApi = ApiClient.getInboxApi();
        Call<List<Message>> call = inboxApi.getInboxMessages();

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messageList.clear();
                    messageList.addAll(response.body());
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
                // TODO: Có thể thêm Toast báo lỗi ở đây nếu cần
            }
        });
    }
}