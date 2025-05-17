package com.example.projectmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<String>  videoUrls;
    private ExoPlayer player;
    private int playingPosition = -1;

//    Constructor
    public VideoAdapter(List<String> videoUrls, ExoPlayer player) {
        this.videoUrls = videoUrls;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {

//        Hàm này chưa build xong
        holder.bind(videoUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        PlayerView playerView;

//       declare what view you want to bind on the view holder
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);
        }

        public void bind(String url){
//            Chỗ này đang viết dở

//            if (getAdapterPosition() == playingPosition && player != null) {
//                playerView.setPlayer(player);
//            } else {
//                playerView.setPlayer(null);
//            }
        }
    }
}
