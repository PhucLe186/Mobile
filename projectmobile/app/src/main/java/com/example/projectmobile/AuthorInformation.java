package com.example.projectmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuthorInformation extends AppCompatActivity {

    private ImageView btnBack, btnBell, btnShare, btnDanhSachVid, btnDangLai;
    private TextView tvName, tvUsername;
    private Button btnFollow, btnMessage;
    private GridView gridVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_information); // Đảm bảo file XML có tên như thế này

        // Ánh xạ view
        btnBack = findViewById(R.id.btn_back);
        btnBell = findViewById(R.id.btn_bell);
        btnShare = findViewById(R.id.btn_share);
        btnDanhSachVid = findViewById(R.id.btn_danhsachvid);
        btnDangLai = findViewById(R.id.btn_danglai);

        tvName = findViewById(R.id.tv_name);
        tvUsername = findViewById(R.id.tv_username);

        btnFollow = findViewById(R.id.btnFollow);
        btnMessage = findViewById(R.id.btnMessage);

        gridVideos = findViewById(R.id.grid_videos);

        // Gán sự kiện click
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

        // Có thể thêm dữ liệu giả cho GridView nếu muốn
        // setupDummyGrid();
    }

    // Ví dụ thêm video giả vào GridView (tuỳ chọn)
    /*
    private void setupDummyGrid() {
        List<Integer> videoThumbs = Arrays.asList(
            R.drawable.thumb1, R.drawable.thumb2, R.drawable.thumb3
        );
        gridVideos.setAdapter(new VideoGridAdapter(this, videoThumbs));
    }
    */
}