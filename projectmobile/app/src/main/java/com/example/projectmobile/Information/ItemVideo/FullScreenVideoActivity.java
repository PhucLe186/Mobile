package com.example.projectmobile.Information.ItemVideo;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.projectmobile.R;

public class FullScreenVideoActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer player;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_full_screen_video);
        playerView = findViewById(R.id.playerView);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        String url = getIntent().getStringExtra("video_url");

        MediaItem mediaItem  = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(player != null){
            player.release();
        }
    }
}