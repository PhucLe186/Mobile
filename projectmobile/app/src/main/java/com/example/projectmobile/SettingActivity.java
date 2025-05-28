package com.example.projectmobile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {


    private ArrayList<SettingItem> arraycontact = new ArrayList<>();
    private RecyclerView List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.setting);

        KhoiTaoDaTa();

        List = findViewById(R.id.Setting);
        List.setLayoutManager(new LinearLayoutManager(this));
        SettingAdapter adapter = new SettingAdapter(this, arraycontact);
        List.setAdapter(adapter);
    }

    private void KhoiTaoDaTa() {
        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Cài đặt chung"));

            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Tài Khoản"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Quyền riêng tư"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Bảo mật & quyền"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Chia sẻ hồ sơ"));


        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Cài đặt chung"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Thông báo"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Live"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Trung tâm hoạt động"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Tùy chọn nội dung"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Kiểm soát khán giả"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Quảng cáo"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Phát"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Hiển Thị"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Ngôn ngữ"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Thời gian sử dụng màn hình"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Gia đình thông minh"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Trợ năng"));

        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Bộ nhớ đệm & dữ liệu di động"));

        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Video ngoại tuyến"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Giải phóng dung lượng"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Trình tiết kiệm dữ liệu"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Hình nền"));

        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Hỗ trợ và giới thiệu"));

        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Báo cáo vấn đề"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Hỗ trợ"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Điều khoản và chính sách"));

        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Đăng nhập"));

        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Chuyển đổi tài khoản"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Đăng xuất"));


    }
}
