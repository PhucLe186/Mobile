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
import com.example.projectmobile.Notification.model.Message;
import com.example.projectmobile.Notification.model.MessageResponse;
import com.example.projectmobile.R;
import com.example.projectmobile.Utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

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
            Message mgs = messageList.get(position);

            int selectedchat = mgs.getMessage_id();
            if (selectedchat == -1) {
                return;
            }
            Intent intent = new Intent(context, ChatActivity.class);

            int seleted;
            if(mgs.getMyself()==1){
                seleted=mgs.getReceiver_id();
                intent.putExtra("id",seleted );
                intent.putExtra("avt", mgs.getReceiver_avatar());
                intent.putExtra("name", mgs.getReceiver_username());
                Toast.makeText(getContext(), "vai "+seleted, Toast.LENGTH_SHORT).show();
            }
            else {
                seleted=mgs.getSender_id();
                intent.putExtra("id", mgs.getSender_id());
                intent.putExtra("avt", mgs.getSender_avatar());
                intent.putExtra("name", mgs.getSender_username());
                Toast.makeText(getContext(), "vai "+seleted, Toast.LENGTH_SHORT).show();
            }

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
                        List<Message> grouped = MessageUtils.groupMessages(response.body().getData());
                        messageList.addAll(grouped);
                    }
                    messageAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Không thể tải tin nhắn", Toast.LENGTH_SHORT).show();
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