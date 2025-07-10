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

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends Fragment {

    private Socket socket;
    private ListView inboxListView;
    private ArrayList<Message> messageList;
    private MessageAdapter messageAdapter;
    private Context context;
    private int user_id;
    private String token;
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

        SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        token = prefs.getString("token", null);
        loadMessagesFromApi(token);
//        user_id = Integer.parseInt(new DecodeToken().getUserIdFromToken(token));


//        connectsocket();

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

//    private void connectsocket() {
//        socket = SocketConfig.getSocket(token);
//        if (socket.connected()) {
//            socket.emit("joinAllrooms");
//            return;
//        }
//        socket.connect();
//        socket.on(Socket.EVENT_CONNECT, args -> {
//            try {
//                socket.emit("joinAllrooms");
//
//            } catch (RuntimeException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        socket.on("update_receive", args ->{
//            requireActivity().runOnUiThread(() -> {
//                try {
//                    JSONObject msg=(JSONObject) args[0];
//                    int sender_id=msg.getInt("sender_id");
//                    int receiver_id=msg.getInt("receiver_id");
//                    String name= msg.getString("name");
//                    String message= msg.getString("message");
//                    String avt=msg.getString("avata_url");
//                    String time=msg.getString("sent_at");
//
//
//                    {
//                        Message mess = new Message();
//
//
//                        mess.setSender_id(sender_id);
//                        mess.setSender_username(name);
//                        mess.setSender_avatar(avt);
//                        mess.setSent_at(time);
//                        mess.setMyself(0);
//                        mess.setMessage(message);
//
//
//
//                        // Nhóm lại các tin nhắn
//                        List<Message> groupedMessages = MessageUtils.groupMessages(Collections.singletonList(mess));
//                        messageList.clear();
//                        messageList.add((Message) groupedMessages);
//
//                        // Cập nhật giao diện
//                        messageAdapter.notifyDataSetChanged();
//
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        });
//
//    }

    private void loadMessagesFromApi( String token) {

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
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Lỗi kết nối API: " + t.getMessage(), t);
            }
        });
    }
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (socket != null) {
//            socket.off("update_receive");
//            socket.disconnect();
//            SocketConfig.disconnectSocket();
//        }
//    }
@Override
public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) {
        loadMessagesFromApi(token); // khi fragment hiện lại
    }
}
}