package com.example.projectmobile;

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

public class PreviewVideo extends AppCompatActivity {
    private PlayerView playerView;
    private ExoPlayer player;
    private Button btnContinue;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_preview_video);

        playerView = findViewById(R.id.playerView);
        btnContinue = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);

        String videoUriString = getIntent().getStringExtra("videoUri");
        Uri videoUri = Uri.parse(videoUriString);

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