package com.example.projectmobile.Auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthApi;
import com.example.projectmobile.Auth.AuthModule.LoginRequest;
import com.example.projectmobile.Auth.AuthModule.LoginResponse;
import com.example.projectmobile.MainActivity;
import com.example.projectmobile.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextView btnRegister, txtError;
    private EditText edtUsername, edtPassword;
    private AppCompatButton btnLogin;
    private AuthApi auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        initViews();
        setListeners();
    }
    private void initViews() {//init all views
        btnLogin = findViewById(R.id.btn_login);//login button
        backBtn = findViewById(R.id.btn_back);//go back button
        btnRegister = findViewById(R.id.btn_register);//register button
        edtUsername = findViewById(R.id.edit_text_name);//username edit text
        edtPassword = findViewById(R.id.edit_txt_password);//password edit text
        txtError = findViewById(R.id.txt_error);//error text
    }
    private void setListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        backBtn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    v.performClick();
                    break;
            }
            return true;
        });

    }

    private void handleLogin() {

        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        auth = ApiClient.getClient().create(AuthApi.class);
        LoginRequest loginModule= new LoginRequest(username, password);
        auth.Login(loginModule).enqueue(new Callback<LoginResponse>() {


            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    String token = response.body().getToken();
                    SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("token", token);
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}