package com.example.projectmobile.Search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.ApiService;
import com.example.projectmobile.R;
import com.example.projectmobile.Search.model.UserResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    AutoCompleteTextView editTextSearch;
    TextView btnSearch;
    ListView listViewResults;

    ArrayList<String> userList = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    ImageView onBach;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        onBach=findViewById(R.id.btnBack);
        editTextSearch = findViewById(R.id.editTextSearch);
        btnSearch = findViewById(R.id.btnSearch);
        listViewResults = findViewById(R.id.listViewResults);

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listViewResults.setAdapter(listAdapter);
        listViewResults.setOnItemClickListener((parent, view, position, id) -> {

            String selectedItem = userList.get(position);
                Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchActivity.this, UserDetailActivity.class);
            intent.putExtra("username", selectedItem );
            startActivity(intent);
        });


        apiService = ApiClient.getClient().create(ApiService.class);

        onBach.setOnClickListener(v -> {
            finish();
        });
        btnSearch.setOnClickListener(v -> {
            String keyword = editTextSearch.getText().toString().trim();
            if (!keyword.isEmpty()) {
                searchUser(keyword);
            } else {
                Toast.makeText(this, "Nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                if (!keyword.isEmpty()) {
                    searchUser(keyword);
                }
            }
        });
    }

    private void searchUser(String keyword) {
        apiService.searchUsers(keyword).enqueue(new Callback<List<UserResult>>() {
            @Override
            public void onResponse(Call<List<UserResult>> call, Response<List<UserResult>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList.clear();
                    for (UserResult user : response.body()) {
                        String username = user.getUsername().toLowerCase();
                        String caption = user.getCaption().toLowerCase();

                        if (username.contains(keyword)) {
                            userList.add(user.getUsername());
                        } else if (caption.contains(keyword)) {
                            userList.add(user.getCaption());
                        }
                    }
                    listAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SearchActivity.this, "Không có kết quả", Toast.LENGTH_SHORT).show();
                }
                }

            @Override
            public void onFailure(Call<List<UserResult>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}