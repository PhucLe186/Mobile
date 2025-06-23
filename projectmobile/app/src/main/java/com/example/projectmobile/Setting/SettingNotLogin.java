package com.example.projectmobile.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.Auth.LoginActivity;
import com.example.projectmobile.R;

import java.util.ArrayList;

public class SettingNotLogin extends AppCompatActivity {


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
        arraycontact.add(new SettingItem(SettingItem.TYPE_ITEM, R.drawable.ic_launcher_background, "Tài Khoản của tôi"));
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
    }
    public void onSettingItemClick(SettingItem item) {
        String title = item.getTitle();

        switch (title) {
            case "Tài Khoản của tôi":
                Intent intent= new Intent(SettingNotLogin.this, LoginActivity.class);
                startActivity(intent);
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

        }
    }
}
