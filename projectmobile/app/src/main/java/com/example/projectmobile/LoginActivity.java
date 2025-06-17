package com.example.projectmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthAPIService;
import com.example.projectmobile.Model.ErrorResponse;
import com.example.projectmobile.Model.UserModel.LoginRequest;
import com.example.projectmobile.Model.UserModel.LoginResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextView btnRegister, txtError;
    private EditText edtUsername, edtPassword;
    private AppCompatButton btnLogin;
    private AuthAPIService authAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        initViews();//Initialize All needed view
        setListeners();//set listener on interacted views
    }

    private void initViews() {//init all views
        btnLogin = findViewById(R.id.btn_login);//login button
        backBtn = findViewById(R.id.btn_back);//go back button
        btnRegister = findViewById(R.id.btn_register);//register button
        edtUsername = findViewById(R.id.edit_text_name);//username edit text
        edtPassword = findViewById(R.id.edit_txt_password);//password edit text
        txtError = findViewById(R.id.txt_error);//error text
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());//login init when clicking on it

        backBtn.setOnTouchListener((v, event) -> {//effect on each button
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

        btnRegister.setOnClickListener(v -> {//TODO go to register page, will be implemented later
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {// handling the LOGIN LOGIC (~_~)
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (!validateLogin(username, password)) {//CHECK VALIDATION ON FIELDS
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        authAPIService = ApiClient.getClient().create(AuthAPIService.class);//CREATE API SERVICE
        LoginRequest request = new LoginRequest(username, password);//CREATE REQUEST OBJECT
        Call<LoginResponse> call = authAPIService.login(request);//MAKE CALL

        call.enqueue(new Callback<LoginResponse>() {//RESPONSE CALL HANDLING
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {//WHEN SUCCESS
                    LoginResponse loginResponse = response.body();
                    Log.d("LOGIN", new Gson().toJson(loginResponse));//LOG TO DEBUG
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));//MAKE INTENT
                } else {//IF NOT SUCCESS
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {//IF ERROR OCCURS
                txtError.setText("Network error: " + t.getMessage());
                txtError.setVisibility(View.VISIBLE);
                hideErrorAfterDelay();
            }
        });
    }

    private void handleErrorResponse(Response<LoginResponse> response) {//handling error response
        try {
            if (response.errorBody() != null) {
                String errorJson = response.errorBody().string();
                ErrorResponse error = new Gson().fromJson(errorJson, ErrorResponse.class);
                Log.d("LOGIN_ERROR", new Gson().toJson(error));//log to debug
                txtError.setText(error.getError());//display onto screen =D
            } else {
                txtError.setText("Unexpected error occurred");//display on to screen too
            }
        } catch (Exception e) {//CATCH ANOTHER ERROR
            e.printStackTrace();
            txtError.setText("An error occurred while parsing error response");
        }

        txtError.setVisibility(View.VISIBLE);
        hideErrorAfterDelay();
    }

    private void hideErrorAfterDelay() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            txtError.setVisibility(View.GONE);
        }, 10000);
    }

    private boolean validateLogin(String username, String password) {//CHECK FIELDS
        return username != null && !username.isEmpty()
                && password != null && !password.isEmpty();
    }
}
