package com.example.projectmobile.Information.EditInformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.EditProfile;
import com.example.projectmobile.Information.EditInformation.Model.Name;
import com.example.projectmobile.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditName extends AppCompatActivity {
    private String name, token;
    private TextView Save, Cancel;
    private EditText Name;
    private EditProfile api;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_name);

        name=getIntent().getStringExtra("name");
        Toast.makeText(this, name, Toast.LENGTH_SHORT ).show();

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        token = "Bearer " + prefs.getString("token", "");

        api= ApiClient.getClient().create(EditProfile.class);

        initView();
        setOnclick();
        Name.setText(name);

    }

    private void setOnclick() {
        Cancel.setOnClickListener(v -> {
            finish();
        });
        Save.setOnClickListener(v -> {
            String newName=Name.getText().toString().trim();
                changeName(newName);
        });
    }
    private void initView() {

        Name=findViewById(R.id.name);
        Save=findViewById(R.id.save);
        Cancel=findViewById(R.id.cancel);
        Save.setEnabled(false);
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentInput = s.toString().trim();

                Save.setEnabled(!currentInput.equals(name));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void changeName(String newName) {

        api.EditName(token,new Name(newName)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                startActivity(new Intent(ActivityEditName.this, ActivityEditAvata.class ));
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }


}
