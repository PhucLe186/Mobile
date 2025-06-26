package com.example.projectmobile.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.Auth.ChangePasswordActivity;
import com.example.projectmobile.MainActivity;
import com.example.projectmobile.R;

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

        ImageView btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v ->
            finish()
        );
    }


    private void KhoiTaoDaTa() {
        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Cài đặt chung"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Tài Khoản"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Quyền riêng tư"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Bảo mật & quyền"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Cài đặt chung"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Thông báo"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Trung tâm hoạt động"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Tùy chọn nội dung"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Phát"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Hiển Thị"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Hỗ trợ và giới thiệu"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Báo cáo vấn đề"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Hỗ trợ"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Điều khoản và chính sách"));
        arraycontact.add(new SettingItem(SettingItem.TYPE_HEADER, 0, "Đăng nhập"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Đổi mật khẩu"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Chuyển đổi tài khoản"));
            arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Đăng xuất"));
    }
    public void handleSettingClick(SettingItem item) {
        String title = item.getTitle();

        switch (title) {
            case "Tài Khoản":

                break;
            case "Quyền riêng tư":

                break;
            case "Bảo mật & quyền":

                break;
            case "Thông báo":

                break;
            case "Tùy chọn nội dung":

                break;
            case "Phát":

                break;
            case "Hiển Thị":

                break;

            case "Báo cáo vấn đề":

                break;
            case "Hỗ trợ":

                break;
            case "Điều khoản và chính sách":

                break;
            case "Chuyển đổi tài khoản":

                break;
            case "Đăng xuất":
                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("token");
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case "Đổi mật khẩu":
                Intent changePassIntent = new Intent(this, ChangePasswordActivity.class);
                startActivity(changePassIntent);
                break;
        }
    }
}
