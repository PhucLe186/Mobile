package com.example.projectmobile;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.VideoCapture.OutputFileOptions;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.camera.core.VideoCapture.OnVideoSavedCallback;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CreateVideoActivity extends AppCompatActivity {

    private PreviewView previewView;
    @SuppressLint("RestrictedApi")
    private VideoCapture videoCapture;
    private boolean isRecording = false;

    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        previewView = findViewById(R.id.preview_view);
        executor = ContextCompat.getMainExecutor(this);
        ImageButton btnRecord = findViewById(R.id.btn_record);
        ImageView btnBack = findViewById(R.id.btn_back);

        //Back Button
        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnRecord.setOnClickListener(v -> {
            if (isRecording) {
                stopRecording();
                isRecording = false;
                btnRecord.setImageResource(android.R.drawable.ic_btn_speak_now);
            } else {
                isRecording = true;
                startRecording();
                btnRecord.setImageResource(android.R.drawable.ic_media_pause);
            }
            isRecording = !isRecording;
        });
    }

    private final ActivityResultLauncher<String[]> requestPermissions=
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean cameraGranted = result.get(android.Manifest.permission.CAMERA);
                Boolean audioGranted = result.get(android.Manifest.permission.RECORD_AUDIO);

                if (Boolean.TRUE.equals(cameraGranted) && Boolean.TRUE.equals(audioGranted)) {
                    startCamera();
                }
                else{
                    Toast.makeText(this, "Cần cấp quyền Camera và Micro", Toast.LENGTH_SHORT).show();
                }
            });

    @SuppressLint("RestrictedApi")
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                videoCapture = new VideoCapture.Builder()
                        .setVideoFrameRate(60)
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                        .build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, videoCapture);

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, executor);
    }

    @SuppressLint("RestrictedApi")
    private void stopRecording() {
        if (videoCapture != null) {
            videoCapture.stopRecording();
        }
    }

    @SuppressLint("RestrictedApi")
    private void startRecording() {
        //check if videoCapture is null
        if (videoCapture == null) return;
        // Create a file to save the video
        File file = new File(getExternalFilesDir(null), "video_" + System.currentTimeMillis() + ".mp4");
        // Create an OutputFileOptions object to specify where to save the video
        OutputFileOptions outputFileOptions = new OutputFileOptions.Builder(file).build();
        // Start recording video
        videoCapture.startRecording(outputFileOptions, executor, new OnVideoSavedCallback(){
            //Video Saving
            @Override
            public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                Uri savedUri = Uri.fromFile(file);
                Log.d("CameraX", "Video saved: " + savedUri);
                runOnUiThread(()-> {
                    Toast.makeText(CreateVideoActivity.this, "Đã lưu video:\n" + savedUri, Toast.LENGTH_SHORT).show();
                    Intent intentPreviewVideo = new Intent(CreateVideoActivity.this, PreviewVideo.class);
                    intentPreviewVideo.putExtra("videoUri", savedUri.toString());
                    startActivity(intentPreviewVideo);
                });
            }
            // Error Occur (if it do)
            @Override
            public void onError(int videoCaptureError, @NonNull String message, @NonNull Throwable cause) {
                Log.e("CameraX", "Error recording video: " + message);
                runOnUiThread(() -> Toast.makeText(CreateVideoActivity.this, "Lỗi quay video", Toast.LENGTH_SHORT).show());
            }
        });
    }
}