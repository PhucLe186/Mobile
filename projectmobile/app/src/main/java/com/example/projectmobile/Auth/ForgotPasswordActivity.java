package com.example.projectmobile.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthApi;
import com.example.projectmobile.Auth.AuthModel.ForgotPasswordRequest;
import com.example.projectmobile.Auth.AuthModel.ForgotPasswordResponse;
import com.example.projectmobile.Model.ErrorResponse;
import com.example.projectmobile.R;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edtEmail,edtUserName;
    Button btnSend,btnBack;
    TextView errorText;
    private AuthApi authApi;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        setView();//Init all views
        setListener();//Set listener on elements
        HandleForgotPassword();//forgot password handling
    }

    private void HandleForgotPassword() {
    }

    private void getInput() {

    }

    private void setView() {
        edtEmail = findViewById(R.id.edit_txt_email);
        edtUserName = findViewById(R.id.edit_txt_username);
        btnSend = findViewById(R.id.button_get_password);
        btnBack = findViewById(R.id.btn_back_to_login);
        errorText = findViewById(R.id.txt_error);
    }//set view for all elements
    private void setListener() {
        btnSend.setOnClickListener(v -> {
            getInput();
            forgotPasswordHandler();
        });

    }

    private void forgotPasswordHandler() {
        String email = edtEmail.getText().toString().trim();
        String username = edtUserName.getText().toString().trim();
        if(!isFieldEmpty(email,username)){
            authApi = ApiClient.getClient().create(AuthApi.class);
            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(email,username);
            authApi.forgotPassword(forgotPasswordRequest).enqueue(new Callback<ForgotPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                    if(response.isSuccessful()) {
                        ForgotPasswordResponse forgotPasswordResponse = response.body();
                        if (forgotPasswordResponse != null) {
                            String password = forgotPasswordResponse.getPassword();
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            builder.setTitle("Lấy lại mật khẩu thành công");
                            builder.setMessage("Mật khẩu của bạn là" + password);
                            builder.setPositiveButton("OK",(dialog, which) -> {
                                startActivity(intent);
                            }).show();
                        }
                    }
                    if(!response.isSuccessful()){
                        handleErrorResponse(response);
                    }
                }
                @Override
                public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                    errorText.setText("Network error: " + t.getMessage());
                    errorText.setVisibility(View.VISIBLE);
                    hideErrorAfterDelay();
                }
            });
        }
    }

    private void handleErrorResponse(Response<ForgotPasswordResponse> response) {
        try {
            if (response.errorBody() != null) {
                String errorJson = response.errorBody().string();
                ErrorResponse error = new Gson().fromJson(errorJson, ErrorResponse.class);
                errorText.setText(error.getError());
                errorText.setVisibility(View.VISIBLE);
                hideErrorAfterDelay();
            } else {
                errorText.setText("Unexpected error occurred");
                errorText.setVisibility(View.VISIBLE);
                hideErrorAfterDelay();
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText("An error occurred while parsing error response");
            errorText.setVisibility(View.VISIBLE);
            hideErrorAfterDelay();
        }
    }


    private boolean isFieldEmpty(String email, String username){
        if (email.isEmpty() || username.isEmpty()) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Vui lòng nhập đầy đủ thông tin");
            hideErrorAfterDelay();
            return true;
        } else {
            errorText.setVisibility(View.GONE);
            return false;
        }
    }

    private void hideErrorAfterDelay() {
        new android.os.Handler().postDelayed(() -> {
            errorText.setVisibility(View.GONE);
        }, 10000);
    }
}