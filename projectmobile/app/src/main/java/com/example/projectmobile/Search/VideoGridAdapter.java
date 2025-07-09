package com.example.projectmobile.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.Information.ItemVideo.FullScreenVideoActivity;
import com.example.projectmobile.R;
import com.example.projectmobile.Search.model.VideoItem;

import java.util.List;

public class VideoGridAdapter extends RecyclerView.Adapter<VideoGridAdapter.ViewHolder> {
    Context context; //Context for accessing resources
    private final List<VideoItem> videoList; //List of videos
    private ExoPlayer currentPlayer; //ExoPlayer instance
    /*
     * Get the list of videos from the server, which belong the user
     * then display and play each of them on the screen
     * */
    public VideoGridAdapter(Context context, List<VideoItem> videoList) {
        this.context = context;
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.small_video, parent, false);
        return new ViewHolder(view);
    }//Get the layout for each video, then display it on RecyclerView

    @Override
    public void onBindViewHolder(@NonNull VideoGridAdapter.ViewHolder holder, int position) {
        VideoItem url = videoList.get(position);//Get url of the video
        ExoPlayer player = new ExoPlayer.Builder(context).build();//Create a new ExoPlayer instance
        holder.playerView.setPlayer(player);//Set the ExoPlayer instance to the playerView
        holder.textCaption.setText(url.getCaption());
        Glide.with(context)
                .load(url.getAvatar_url())
                .circleCrop()
                .error(R.drawable.error)
                .into(holder.Avt);

        holder.name.setText(url.getUsername());
        MediaItem mediaItem = MediaItem.fromUri(url.getVideo_url());//Create a new MediaItem instance
        player.setMediaItem(mediaItem);//Set the MediaItem to the ExoPlayer instance
        player.prepare();//Prepare the ExoPlayer instance
        player.setVolume(0f);
        player.setPlayWhenReady(false);//Play the video when visible

        player.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (isPlaying) {
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        if (player.isPlaying()) {
                            player.seekTo(0);           // Quay lại đầu video
                            player.play();              // Tiếp tục phát
                        }
                    }, 5000); // 10 giây = 10.000 ms
                }
            }
        });

        holder.playerView.setOnClickListener(v->{
            Intent intent = new Intent(context, FullScreenVideoActivity.class);
            intent.putExtra("video_url", url.getVideo_url());
            context.startActivity(intent);
        });

        holder.player = player;
    }//Bind the video to the playerView

    @Override
    public int getItemCount() {
        return videoList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        androidx.media3.ui.PlayerView playerView;//Player view to display the video
        TextView textCaption, name;
        ImageView Avt;
        ExoPlayer player;//ExoPlayer will display the video on to PlayerView
        public ViewHolder(@NonNull View itemView) {//gain view from the layout
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);//then find id of playerView in the layout
            textCaption = itemView.findViewById(R.id.textCaption);
            Avt=itemView.findViewById(R.id.avt);
            name=itemView.findViewById(R.id.name);
        }
    }
    public void playVisibleVideos(RecyclerView recyclerView) {
        int centerY = recyclerView.getHeight() / 2;
        int closestPos = -1;
        int closestDistance = Integer.MAX_VALUE;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View view = recyclerView.getChildAt(i);
            int childCenter = (view.getTop() + view.getBottom()) / 2;
            int distance = Math.abs(childCenter - centerY);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPos = recyclerView.getChildAdapterPosition(view);
            }
        }

        if(currentPlayer != null) {
            currentPlayer.setPlayWhenReady(false);
            currentPlayer.release();
        }

        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(closestPos);
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.player.setPlayWhenReady(true);
            currentPlayer = holder.player;
        }
    }
}