package com.example.projectmobile.Information.EditInformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.EditProfile;
import com.example.projectmobile.Information.EditInformation.Model.Information;
import com.example.projectmobile.R;
import com.example.projectmobile.Utils.FileUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditAvata extends AppCompatActivity {


    private int user_id;
    private MultipartBody.Part body;
    private EditProfile api;
    private String token, name;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> imagePickerLauncher;
    private android.net.Uri selectedImageUri;
    private ImageView backImage, profileImage, nameEditIcon, tiktokIdEditIcon,
            bioEditIcon, linkEditIcon, aiSelfEditIcon;
    private TextView  changeImageText, nameValue,
            tiktokIdValue, tiktokLink, bioValue,
            linkValue, aiSelfValue;

    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infor);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        token = "Bearer " + prefs.getString("token", "");

        api= ApiClient.getClient().create(EditProfile.class);

        getInfor(token);
        initView();
        initLauncher();
        onclick();

    }
    private void onclick() {
        changeImageText.setOnClickListener(v -> openImagePicker());
        profileImage.setOnClickListener(v -> openImagePicker());
        backImage.setOnClickListener(v -> finish());
        nameEditIcon.setOnClickListener(v -> {
            Intent intent= new Intent(this, ActivityEditName.class);
            intent.putExtra("name", name);
            startActivity(intent);
        });
    }
    private void initView() {
        backImage = findViewById(R.id.backImage);
        profileImage = findViewById(R.id.profileImage);
        changeImageText = findViewById(R.id.changeImageText);
        nameValue = findViewById(R.id.nameValue);
        nameEditIcon = findViewById(R.id.nameEditIcon);
        tiktokIdValue = findViewById(R.id.tiktokIdValue);
        tiktokIdEditIcon = findViewById(R.id.tiktokIdEditIcon);
        tiktokLink = findViewById(R.id.tiktokLink);
        bioValue = findViewById(R.id.bioValue);
        bioEditIcon = findViewById(R.id.bioEditIcon);
        linkValue = findViewById(R.id.linkValue);
        linkEditIcon = findViewById(R.id.linkEditIcon);
        aiSelfValue = findViewById(R.id.aiSelfValue);
        aiSelfEditIcon = findViewById(R.id.aiSelfEditIcon);
    }
    private void initLauncher() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        profileImage.setImageURI(selectedImageUri);

                        UploadImages(selectedImageUri);
                    }
                }
        );
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }
    private void getInfor(String token){
        Toast.makeText(ActivityEditAvata.this,"ok", Toast.LENGTH_SHORT).show();
    api.getInfor(token).enqueue(new Callback<List<Information>>() {
    @Override
    public void onResponse(Call<List<Information>> call, Response<List<Information>> response) {
        name=response.body().get(0).getUsername();
        nameValue.setText(response.body().get(0).getUsername());
        Glide.with(ActivityEditAvata.this)
                .load(response.body().get(0).getAvatar_url())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .circleCrop()
                .into(profileImage);
    }
    @Override
    public void onFailure(Call<List<Information>> call, Throwable t) {
        Toast.makeText(ActivityEditAvata.this, "Upload thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }
});
    }
    private void UploadImages(Uri imageUri){
        file = FileUtils.getFileFromUri(this, imageUri,".jpg" );

        RequestBody requestFile=RequestBody.create(MediaType.parse("image/*"), file);
        body=MultipartBody.Part.createFormData("image", file.getName(), requestFile);

    if (!file.exists()) {
        Toast.makeText(this, "File không tồn tại!", Toast.LENGTH_SHORT).show();
        return;
    }
        if (token == null) {
        Toast.makeText(this, "Chưa đăng nhập!", Toast.LENGTH_SHORT).show();
        finish();
        return;
        }
        else {
            api.UploadImg(token, body).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ActivityEditAvata.this, "Tải ảnh thành công!", Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("refresh", true);
                        setResult(RESULT_OK, resultIntent);
                    }
                    else {
                        Toast.makeText(ActivityEditAvata.this, "Upload thất bại! Mã lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ActivityEditAvata.this, "Upload thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
}
}