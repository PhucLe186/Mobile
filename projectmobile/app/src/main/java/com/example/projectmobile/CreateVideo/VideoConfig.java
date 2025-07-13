package com.example.projectmobile.CreateVideo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthorApi;
import com.example.projectmobile.MainActivity;
import com.example.projectmobile.R;
import com.example.projectmobile.Utils.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoConfig extends AppCompatActivity {
    private MultipartBody.Part body;
    PlayerView playerView;
    ExoPlayer player;
    AuthorApi Api;
    Button uploadBtn,backBtn;
    private  EditText description;
    private String token;
    File file;
    Uri videoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_config);

        initView();
        initOnclick();
        initVideoPlayer();

    }
    private void initVideoPlayer() {
        Api= ApiClient.getClient().create(AuthorApi.class);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        token = "Bearer " + prefs.getString("token", "");

        String videoUriString = getIntent().getStringExtra("videoUri");
        if (videoUriString == null) {
            finish();
            return;
        }

        videoUri = Uri.parse(videoUriString);
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

    private void initOnclick() {
        //upload video
        uploadBtn.setOnClickListener(v -> {
            String captionText = description.getText().toString().trim();
            if (captionText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập caption", Toast.LENGTH_SHORT).show();
                return;
            }
            UploadVideo(videoUri, captionText, token);
        });
        //Back
        backBtn.setOnClickListener(v -> {
            finish();
        });
    }
    private void initView() {
        uploadBtn = findViewById(R.id.btn_upload);
        backBtn = findViewById(R.id.btn_back);
        description = findViewById(R.id.editText);
        playerView = findViewById(R.id.playerView);
    }

    private void UploadVideo(Uri videoUri, String captionText, String token){
        file = FileUtils.getFileFromUri(this, videoUri,".mp4" );

        if (file == null || !file.exists()) {
            Toast.makeText(this, "Không tìm được video để upload!", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody requestFile=RequestBody.create(MediaType.parse("video/*"), file);
        body= MultipartBody.Part.createFormData("video", file.getName(), requestFile);

        RequestBody captionPart = RequestBody.create(MediaType.parse("text/plain"), captionText);

        Api.UploadVideo(token, captionPart, body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(VideoConfig.this, "Upload thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VideoConfig.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("UploadError", errorBody);
                        Toast.makeText(VideoConfig.this, "Upload thất bại: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(VideoConfig.this, "Upload thất bại! Mã lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(VideoConfig.this, "Upload thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}