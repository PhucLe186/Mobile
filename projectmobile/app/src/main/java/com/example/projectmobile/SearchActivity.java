package com.example.projectmobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmobile.R;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private EditText editTextSearch;
    private TextView btnSearch;
    private ImageView btnBack;
    private ListView listViewResults;

    private ArrayList<String> searchSuggestions;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Ánh xạ
        editTextSearch = findViewById(R.id.editTextSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnBack = findViewById(R.id.btnBack);
        listViewResults = findViewById(R.id.listViewResults);

        // Dữ liệu gợi ý mẫu
        searchSuggestions = new ArrayList<>();
        searchSuggestions.add("Lặng Từ Nhân Thoại");
        searchSuggestions.add("chai tẩy rửa đa năng");
        searchSuggestions.add("sao mình chưa nắm tay doraemon");
        searchSuggestions.add("nghịch lý 2 đồng xu");
        searchSuggestions.add("under the influence remix");

        // Adapter cho ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchSuggestions);
        listViewResults.setAdapter(adapter);

        // Bắt sự kiện tìm kiếm
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editTextSearch.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    searchSuggestions.add(0, keyword); // Thêm vào đầu danh sách
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SearchActivity.this, "Đã tìm kiếm: " + keyword, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bắt sự kiện click vào từng item trong ListView
        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = searchSuggestions.get(position);
                Toast.makeText(SearchActivity.this, "Bạn đã chọn: " + selected, Toast.LENGTH_SHORT).show();
            }
        });

        // Nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Trở về màn hình trước
            }
        });
    }
}