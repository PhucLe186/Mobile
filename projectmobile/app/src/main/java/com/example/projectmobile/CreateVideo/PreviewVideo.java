package com.example.projectmobile.CreateVideo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.projectmobile.R;

public class PreviewVideo extends AppCompatActivity {
    PlayerView playerView;
    private ExoPlayer player;
    Button btnContinue;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_preview_video);

        playerView = findViewById(R.id.playerView);
        btnContinue = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);

        //Back Button
        btnBack.setOnClickListener(v -> {
            finish();
        });

        //Get video from uri intent
        String videoUriString = getIntent().getStringExtra("videoUri");
        if(videoUriString == null){
            finish();
            return;
        }
        Uri videoUri = Uri.parse(videoUriString);

        //video player
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    player.seekTo(0);
                    player.play();
                }
            }
        });
        //Continue Button
        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(PreviewVideo.this, VideoConfig.class);
            intent.putExtra("videoUri", videoUri.toString());
            startActivity(intent);
            finish();
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}