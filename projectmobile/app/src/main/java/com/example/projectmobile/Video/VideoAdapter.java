package com.example.projectmobile.Video;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.Information.AuthorInformation;
import com.example.projectmobile.Comment.CommentActivity;
import com.example.projectmobile.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<Video> videoList;
    private ExoPlayer player;
    private int currentPlayingPosition = -1;
    private Context context;




    public VideoAdapter(List<Video> videoList, Context context) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        holder.bind(videoList.get(position), position);
    }

//  Playing video when scrolling
    @SuppressLint("NotifyDataSetChanged")
    public void playVideoAtPosition(int position){
        if(position == currentPlayingPosition) return;

        releasePlayer();
        currentPlayingPosition = position;
        notifyDataSetChanged();
    }

    public void releasePlayer() {
        if(player != null){
            player.release();
            player = null;
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

     class VideoViewHolder extends RecyclerView.ViewHolder{
        PlayerView playerView;
        ImageView authorAvatar;
        ImageView commentAvata;
        TextView name, caption, like, comment;


         public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);
            commentAvata = itemView.findViewById(R.id.icon_comment);
            authorAvatar = itemView.findViewById(R.id.user_avatar);
            name=itemView.findViewById(R.id.txt_author_name);
            caption=itemView.findViewById(R.id.txt_description);
            like=itemView.findViewById(R.id.txt_like_count);
            comment=itemView.findViewById(R.id.txt_count_comment);


            authorAvatar.setOnClickListener(v->{
                Intent intentUserInformation = new Intent(context, AuthorInformation.class);
                context.startActivity(intentUserInformation);
            });
            commentAvata.setOnClickListener(v -> {
                Intent intent =new Intent(context, CommentActivity.class);
                context.startActivity(intent);
            });
        }


        public void bind(Video video, int position){
            if(position == currentPlayingPosition){
                //check if player has been created
                if(player == null){
                    player = new ExoPlayer.Builder(context).build();
                    playerView.setPlayer(player);
                    MediaItem mediaItem = MediaItem.fromUri(video.getVideo_url());
                    name.setText(video.getUsername());
                    caption.setText(video.getCaption());
                    comment.setText(String.valueOf(video.getComment_count()));
                    like.setText(String.valueOf(video.getLike_count()));

                    player.setMediaItem(mediaItem);
                    player.prepare();
                    player.play();
                }
                else{
                    playerView.setPlayer(player);
                }
            }
            else{
                playerView.setPlayer(null);
            }
        }
    }
}
