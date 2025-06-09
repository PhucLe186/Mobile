package com.example.projectmobile;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<String> videoUrls;
    private ExoPlayer player;
    private int currentPlayingPosition = -1;
    private Context context;

    public VideoAdapter(List<String> videoUrls, Context context){
        this.context = context;
        this.videoUrls = videoUrls;
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
        holder.bind(videoUrls.get(position), position);
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
        return videoUrls.size();
    }

    //The ViewHolder class for the VideoAdapter
     class VideoViewHolder extends RecyclerView.ViewHolder{
        PlayerView playerView;
        ImageView authorAvatar;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);

            authorAvatar = itemView.findViewById(R.id.user_avatar);
            authorAvatar.setOnClickListener(v->{
                Intent intentUserInformation = new Intent(context, AuthorInformation.class);
                context.startActivity(intentUserInformation);
            });
        }


        public void bind(String videoUrl, int position){
            if(position == currentPlayingPosition){
                //check if player has been created
                if(player == null){
                    player = new ExoPlayer.Builder(context).build();
                    playerView.setPlayer(player);
                    MediaItem mediaItem = MediaItem.fromUri(videoUrl);
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
