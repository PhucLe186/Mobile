package com.example.projectmobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText editTextSearch;
    TextView btnSearch;
    ListView listViewResults;

    ArrayList<String> userList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearch = findViewById(R.id.editTextSearch);
        btnSearch = findViewById(R.id.btnSearch);
        listViewResults = findViewById(R.id.listViewResults);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listViewResults.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String keyword = editTextSearch.getText().toString().trim();
            if (!keyword.isEmpty()) {
                searchUser(keyword);
            } else {
                Toast.makeText(this, "Nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchUser(String keyword) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:5000/api/search?q=" + URLEncoder.encode(keyword, "UTF-8"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();
                    String json = response.toString();

                    JSONArray jsonArray = new JSONArray(json);
                    userList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject user = jsonArray.getJSONObject(i);
                        String name = user.getString("username");
                        String caption = user.optString("caption", "(Không có caption)");
                        String display = "Người dùng: " + name + "\nCaption: " + caption;
                        userList.add(display);
                    }

                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Lỗi server: " + responseCode, Toast.LENGTH_SHORT).show());
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}