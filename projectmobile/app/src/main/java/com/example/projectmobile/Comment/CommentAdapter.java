package com.example.projectmobile.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.Comment.Model.Comment;
import com.example.projectmobile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.userName.setText(comment.getUserName());
        holder.userComment.setText(comment.getComment());
        holder.time.setText((CharSequence) comment.getComment_time());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC")); // vì có 'Z' là UTC
        try {
            Date commentDate = sdf.parse(comment.getComment_time());
            Date currentDate = new Date();

            long diffInMillis = currentDate.getTime() - commentDate.getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);

            if (diffInDays == 0) {
                holder.time.setText("Hôm nay");
            } else {
                holder.time.setText(diffInDays + " ngày trước");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load(comment.getAvatar_url())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .circleCrop()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView userName, userComment, time;
        public ViewHolder( View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.image);
            userName = itemView.findViewById(R.id.usernameText);
            userComment = itemView.findViewById(R.id.commentText);
            time= itemView.findViewById(R.id.timer);
        }
    }

    public static class UploadCommentRes {
        private boolean success;
        private String message;
        private int comment_count;
        public boolean isSuccess() {
            return success;
        }
        public String getMessage() {
            return message;
        }
        public int getComment_count() {return comment_count;}
    }
}
