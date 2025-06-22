package com.example.projectmobile.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class CommentBottomSheet {

    public static void show(Context context, int videoId) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_comment, null);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_comments);
        EditText editComment = view.findViewById(R.id.edit_comment);
        ImageView sendBtn = view.findViewById(R.id.btn_send_comment);

        List<Comment> commentList = new ArrayList<>();
        CommentAdapter adapter = new CommentAdapter(context, commentList );

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        commentList.add(new Comment("https://i.imgur.com/avatar1.jpg", "alice", "Video hay quá!", "sd"));
        commentList.add(new Comment("https://i.imgur.com/avatar2.jpg", "bob", "🤣🤣", "dwswd"));
        adapter.notifyDataSetChanged();

        sendBtn.setOnClickListener(v -> {
            String text = editComment.getText().toString().trim();
            if (!text.isEmpty()) {
                commentList.add(new Comment("https://i.imgur.com/avatar1.jpg", "alice", "Video hay quá!", "sdad"));
                adapter.notifyItemInserted(commentList.size() - 1);
                editComment.setText("");
                recyclerView.scrollToPosition(commentList.size() - 1);
            } else {
                Toast.makeText(context, "Nhập nội dung bình luận!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }
}