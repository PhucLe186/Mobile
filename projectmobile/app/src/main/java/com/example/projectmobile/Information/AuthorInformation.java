package com.example.projectmobile.Information;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthorApi;
import com.example.projectmobile.Notification.ChatActivity;
import com.example.projectmobile.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorInformation extends AppCompatActivity {

    private ImageView btnBack, btnBell, btnShare, btnDanhSachVid, btnDangLai;
    private ImageView imageViewAvatar;
    private TextView tvName, tvUsername, follower, following, likes;
    private Button btnFollow, btnMessage;
    private GridView gridVideos;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_information);

        userId = getIntent().getIntExtra("user_id", -1);
        if (userId == -1) {
            Toast.makeText(this, "Không có user_id", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initView();
        setupClick();
        fetchAuthorInfo();
    }

    public void initView(){
        btnBack = findViewById(R.id.btn_back);
        btnBell = findViewById(R.id.btn_bell);
        btnShare = findViewById(R.id.btn_share);
        btnDanhSachVid = findViewById(R.id.btn_danhsachvid);
        btnDangLai = findViewById(R.id.btn_danglai);
        imageViewAvatar=findViewById(R.id.avatar);
        tvName = findViewById(R.id.tv_name);
        tvUsername = findViewById(R.id.tv_username);
        btnFollow = findViewById(R.id.btnFollow);
        btnMessage = findViewById(R.id.btnMessage);
        gridVideos = findViewById(R.id.grid_videos);
        follower = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        likes = findViewById(R.id.likes);
    }

    public void setupClick(){
        btnBack.setOnClickListener(v -> finish());
        btnBell.setOnClickListener(v ->
                Toast.makeText(this, "Thông báo", Toast.LENGTH_SHORT).show()
        );

        btnShare.setOnClickListener(v ->
                Toast.makeText(this, "Chia sẻ hồ sơ", Toast.LENGTH_SHORT).show()
        );

        btnFollow.setOnClickListener(v ->
                Toast.makeText(this, "Đã follow người dùng", Toast.LENGTH_SHORT).show()
        );

        btnMessage.setOnClickListener(v -> {
            Intent intent = new Intent(AuthorInformation.this, ChatActivity.class);
            startActivity(intent);
        });

        btnDanhSachVid.setOnClickListener(v ->
                Toast.makeText(this, "Danh sách video", Toast.LENGTH_SHORT).show()
        );

        btnDangLai.setOnClickListener(v ->
                Toast.makeText(this, "Đã đăng lại video", Toast.LENGTH_SHORT).show()
        );
    }

    private void fetchAuthorInfo() {
        AuthorApi getauthor = ApiClient.getClient().create(AuthorApi.class);
        Call<AuthorInfo> call = getauthor.getAuthorInfor(userId);
        Log.d("AuthorInfo", "Fetching info for userId = " + userId);

        call.enqueue(new Callback<AuthorInfo>() {
            @Override
            public void onResponse(Call<AuthorInfo> call, Response<AuthorInfo> response) {
                if (response.body() != null) {
                    AuthorInfo author = response.body();
                    updateUI(author);
                } else {
                    Toast.makeText(AuthorInformation.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthorInfo> call, Throwable t) {
                Toast.makeText(AuthorInformation.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(AuthorInfo info){
        tvName.setText(info.getUsername());
        tvUsername.setText("@" + info.getUsername());
        follower.setText(info.getFollower_count() + "\nFollower");
        following.setText(info.getFollowing_count() + "\nĐã follower");
        likes.setText(String.valueOf(info.getLike_count())+ "\nThích" );


        Glide.with(this)
                .load(info.getAvatar_url())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .circleCrop()
                .into(imageViewAvatar);
    }
}
