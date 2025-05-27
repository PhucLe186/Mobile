package com.example.projectmobile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText editTextSearch;
    ListView listViewResults;
    List<String> allVideos;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        // Áp dụng padding cho hệ thống thanh điều hướng/status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần UI
        editTextSearch = findViewById(R.id.editTextSearch);
        listViewResults = findViewById(R.id.listViewResults);

        // Dữ liệu mẫu
        allVideos = new ArrayList<>();
        allVideos.add("Funny cat video");
        allVideos.add("Dance challenge 2025");
        allVideos.add("Cooking with mom");
        allVideos.add("Travel to Da Lat");
        allVideos.add("TikTok trend mới");

        // Adapter và ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allVideos);
        listViewResults.setAdapter(adapter);

        // Tìm kiếm động
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}
