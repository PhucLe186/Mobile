package com.example.projectmobile.Video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.Comment.CommentBottomSheet;
import com.example.projectmobile.Information.Author.AuthorInformation;
import com.example.projectmobile.R;
import com.example.projectmobile.Video.CallApi.LikeCall;
import com.example.projectmobile.Video.CallApi.LikeHelper;
import com.example.projectmobile.Video.model.Video;

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
    @SuppressLint("NotifyDataSetChanged")
    public void playVideoAtPosition(int position){
        if(position == currentPlayingPosition) return;
        releasePlayer();
        currentPlayingPosition = position;
        notifyDataSetChanged();
    }
    public void pausePlayer() {
        if(player != null && player.isPlaying()) {
            player.pause();
        }
    }
    public void Play() {
        if(player != null && !player.isPlaying()) {
            player.play();
        }
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
        ImageView commentAvata, icon_like;
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
            icon_like=itemView.findViewById(R.id.icon_like);

        }
         @OptIn(markerClass = UnstableApi.class)
         public void bind(Video video, int position){
             name.setText(video.getUsername());
             caption.setText(video.getCaption());
             comment.setText(String.valueOf(video.getComment_count()));
             like.setText(String.valueOf(video.getLike_count()));

             Glide.with(context)
                     .load(video.getAvatar_url())
                     .placeholder(R.drawable.user)
                     .error(R.drawable.user)
                     .circleCrop()
                     .into(authorAvatar);
             icon_like.setImageResource(video.getLiked() == 1 ? R.drawable.favorite_red : R.drawable.favorite);

             MediaSource mediaSource = new ProgressiveMediaSource.Factory(
                     new DefaultHttpDataSource.Factory()
             ).createMediaSource(MediaItem.fromUri(video.getVideo_url()));

            if(position == currentPlayingPosition){
                if(player == null){
                    player = new ExoPlayer.Builder(context).build();
                    playerView.setPlayer(player);

                    player.setMediaSource(mediaSource);
                    player.prepare();
                    player.setPlayWhenReady(true);

//                    MediaItem mediaItem = MediaItem.fromUri(video.getVideo_url());
                    player.addListener(new Player.Listener() {
                        @Override
                        public void onPlaybackStateChanged(int playbackState) {
                            Player.Listener.super.onPlaybackStateChanged(playbackState);
                            if (playbackState == player.STATE_ENDED) {
                                player.seekTo(0);
                                player.play();
                            }
                        }
                    });

                    authorAvatar.setOnClickListener(v->{
                        Intent intentUserInformation = new Intent(context, AuthorInformation.class);
                        intentUserInformation.putExtra("user_id", video.getUser_id());
                        context.startActivity(intentUserInformation);
                    });

                    commentAvata.setOnClickListener(view -> {
                        CommentBottomSheet.show(context, video.getVideo_id(), new CommentBottomSheet.OnCommentChangedListener() {
                            @Override
                            public void onCommentChanged(int newCommentCount) {
                                // Cập nhật số comment ở đây
                                video.setComment_count((newCommentCount));
                                comment.setText(String.valueOf(newCommentCount));
                            }
                        });
                    });


                    icon_like.setOnClickListener(v -> {
                        int newLikedState = video.getLiked() == 1 ? 0 : 1;
                        int likeCountChange = newLikedState == 1 ? 1 : -1;

                        video.setLiked(newLikedState);
                        video.setLike_count(video.getLike_count() + likeCountChange);
                        icon_like.setImageResource(newLikedState == 1 ? R.drawable.favorite_red : R.drawable.favorite);
                        like.setText(String.valueOf(video.getLike_count()));

                        LikeHelper.LAUvideo(context, video.getVideo_id(), new LikeCall() {
                            @Override
                            public void onSuccess() {
                            }
                            public void onFailure(String errorMessage) {
                                video.setLiked(newLikedState == 1 ? 0 : 1);
                                video.setLike_count(video.getLike_count() - likeCountChange);
                                icon_like.setImageResource(newLikedState == 1 ? R.drawable.favorite : R.drawable.favorite_red);
                                like.setText(String.valueOf(video.getLike_count()));
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });

                    });
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
