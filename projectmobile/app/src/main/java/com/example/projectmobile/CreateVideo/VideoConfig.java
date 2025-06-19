package com.example.projectmobile.CreateVideo;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.projectmobile.R;

public class VideoConfig extends AppCompatActivity {
    PlayerView playerView;
    ExoPlayer player;
    Button uploadBtn,backBtn;
    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_config);
        uploadBtn = findViewById(R.id.btn_upload);
        backBtn = findViewById(R.id.btn_back);
        description = findViewById(R.id.editText);
        playerView = findViewById(R.id.playerView);
        //Upload
        uploadBtn.setOnClickListener(v -> {
            //TODO: Upload video to server
            //TODO:Upload description to server
        });
        //Back
        backBtn.setOnClickListener(v -> {
            finish();
        });
        String videoUriString = getIntent().getStringExtra("videoUri");
        if (videoUriString == null) {
            finish();
            return;
        }
        Uri videoUri = Uri.parse(videoUriString);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setRepeatMode(Player.REPEAT_MODE_ONE);
        playerView.setOnClickListener(v -> {
            if(player.isPlaying()){
                player.pause();
            }
            else{
                player.play();
            }
        });

    }
}