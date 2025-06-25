package com.example.projectmobile.Comment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.CommentApi;
import com.example.projectmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentBottomSheet {
    private static CommentApi commentApi;
    private static String avatar_url;
    private static String username;
    private static String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    public static void show(Context context, int videoId) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_comment, null);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_comments);
        EditText editComment = view.findViewById(R.id.edit_comment);
        ImageView sendBtn = view.findViewById(R.id.btn_send_comment);

        List<Comment> commentList = new ArrayList<>();//Fetch data and pass to adapter
        CommentAdapter adapter = new CommentAdapter(context, commentList );

        //Get token from shared preferences
        SharedPreferences pref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        String token = pref.getString("token", null);
        editor.putString("token", token);
        editor.apply();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        commentApi = ApiClient.getClient().create(CommentApi.class);
        getComments(adapter,commentList, videoId);
        getUserAvatar(token);

        //Send User Comment
        sendBtn.setOnClickListener(v -> {
            String text = editComment.getText().toString().trim();
            if (!text.isEmpty()) {
                commentList.add(new Comment(avatar_url, username, text, currentTime));
                adapter.notifyItemInserted(commentList.size() - 1);
                editComment.setText("");
                recyclerView.scrollToPosition(commentList.size() - 1);
                sendCommentToServer(token,videoId, text,context);//Send comment to server need token to get user_id
            } else {
                Toast.makeText(context, "Nhập nội dung bình luận!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private static void sendCommentToServer(String token,int video_id, String comment,Context context) {
        commentApi.uploadComment("Bearer "+ token, new UploadCommentReq(video_id, comment, currentTime)).enqueue(
        new Callback<UploadCommentRes>() {
            @Override
            public void onResponse(@NonNull Call<UploadCommentRes> call, @NonNull Response<UploadCommentRes> response) {
                if(response.isSuccessful() &&   response.body() != null){
                    UploadCommentRes res = response.body();
                    if(response.body().isSuccess()){
                       Log.d("comment",res.getMessage());
                       Toast.makeText(context, "Your comment has been upload!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d("comment",res.getMessage());
                        Toast.makeText(context, "Your comment can not be uploaded!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.e("comment", String.valueOf(response.code()));
                    Toast.makeText(context, "Your comment can not be uploaded!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<UploadCommentRes> call, @NonNull Throwable t) {
                Log.d("comment", "Error Occur:" + t.getMessage());
                Toast.makeText(context, "Your comment can not be uploaded!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static void getComments(CommentAdapter adapter, List<Comment> commentList,int videoId) {
        commentApi.getComments(videoId).enqueue(new Callback<CommentRes>() {
            @Override
            public void onResponse(@NonNull Call<CommentRes> call, @NonNull Response<CommentRes> response) {
                if(response.isSuccessful()){
                    CommentRes commentRes = response.body();
                    Log.d("comment", String.valueOf(commentRes.getData()));
                    for(Comment c : commentRes.getData()){
                        String avatar_url = c.getAvatar_url();
                        String username = c.getUserName();
                        String comment = c.getComment();
                        String created_at = c.getComment_time();
                        commentList.add(new Comment(avatar_url,username,comment,created_at));
                        Log.d("comment", avatar_url + username + comment + created_at);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{//No Comment found handling right here   ~(@.@)~
                    Log.d("comment", "NO COMMENT FOUND");
                }
            }
            @Override
            public void onFailure(@NonNull Call<CommentRes> call, @NonNull Throwable t) {
                Log.d("Comment", "Error Occur!");
            }
        });
    }

    private static void getUserAvatar(String token){
        commentApi.getInfo("Bearer " + token).enqueue(new Callback<GetUserInfoRes>() {
            @Override
            public void onResponse(@NonNull Call<GetUserInfoRes> call, @NonNull Response<GetUserInfoRes> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;


                    //Avatar URL here =)
                    avatar_url = response.body().getAvatar_url();
                    username = response.body().getUserName();
                    //=====================//


                    Log.d("avatar",avatar_url);
                }
                else{
                    Log.d("avatar", "Avatar Not Found");
                }
            }

            @Override
            public void onFailure(Call<GetUserInfoRes> call, Throwable t) {
                Log.e("avatar", "Connect Error: " + t.getMessage(), t);
            }
        });
    }
}