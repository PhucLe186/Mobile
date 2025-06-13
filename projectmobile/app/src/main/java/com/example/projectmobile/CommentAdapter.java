package com.example.projectmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
        private List<Comment> commentList;
        private Context context;

        public CommentAdapter(Context context, List<Comment> commentList) {
            this.context = context;
            this.commentList = commentList;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_comment, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder( ViewHolder holder, int position) {
            Comment comment = commentList.get(position);
            holder.userName.setText(comment.getUserName());
            holder.userComment.setText(comment.getComment());
        }

        @Override
        public int getItemCount() {
            return commentList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView userName, userComment;

            public ViewHolder( View itemView) {
                super(itemView);
                userName = itemView.findViewById(R.id.userName);
                userComment = itemView.findViewById(R.id.userComment);
            }
        }
    }

