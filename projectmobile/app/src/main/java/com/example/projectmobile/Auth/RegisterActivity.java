package com.example.projectmobile.Auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthApi;
import com.example.projectmobile.model.ErrorResponse;
import com.example.projectmobile.Auth.AuthModel.RegisterRequest;
import com.example.projectmobile.Auth.AuthModel.RegisterResponse;
import com.example.projectmobile.Module.CaptchaGenerator;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.projectmobile.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView captchaImage, back_btn;
    TextView loginBtn,txtError;
    EditText captchaInput, edit_txt_email, edit_txt_name, edit_txt_password, edit_txt_re_password;
    Button btnVerify;
    Button btnRefresh;
    private AuthApi authApi;

    CaptchaGenerator captchaGenerator;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        initView();//Initialize XML view
        captchaGenerator = new CaptchaGenerator();//Create Captcha Generator Object
        loadCaptcha();//Load Captcha
        btnRefresh.setOnClickListener(v -> {//Reset captcha when user clicking on
            loadCaptcha();
        });

        btnVerify.setOnClickListener(v -> {
            String email = edit_txt_email.getText().toString().trim();
            String name = edit_txt_name.getText().toString().trim();
            String password = edit_txt_password.getText().toString().trim();
            String repass = edit_txt_re_password.getText().toString().trim();
            if(repassHandle(password,repass) && !isEmptyField(email,name,password,repass)){
                String input = captchaInput.getText().toString().trim();
                if (input.equalsIgnoreCase(captchaGenerator.getCaptchaCode())) {
                    handleRegister(email,name, password);
                } else {
                    txtError.setText("Sai captcha");
                    txtError.setVisibility(View.VISIBLE);
                    hideErrorAfterDelay();
                    loadCaptcha();
                }
            }
        });

        loginBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
        );

        back_btn = (ImageView) findViewById(R.id.btn_back);

        back_btn.setOnTouchListener((v, event) -> {
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
    private void initView() {
        captchaImage = findViewById(R.id.captchaImage);
        captchaInput = findViewById(R.id.captchaInput);
        btnVerify = findViewById(R.id.btnVerify);
        btnRefresh = findViewById(R.id.btnRefresh);
        loginBtn = findViewById(R.id.btn_login);
        edit_txt_email = findViewById(R.id.edit_txt_email);
        edit_txt_name = findViewById(R.id.edit_txt_name);
        edit_txt_password = findViewById(R.id.edit_txt_password);
        edit_txt_re_password = findViewById(R.id.edit_txt_re_password);
        txtError = findViewById(R.id.txt_error);
    }

    private boolean isEmptyField(String email, String name, String password, String repass) {
        if(email.isEmpty() || name.isEmpty() || password.isEmpty() || repass.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    private boolean repassHandle(String password, String repass) {
        if(!password.equals(repass)){
            txtError.setText("Mật khẩu không khớp");
            txtError.setVisibility(View.VISIBLE);
            hideErrorAfterDelay();
            return false;
        }
        return true;
    }

    private void handleRegister(String email, String name, String password) {
        authApi = ApiClient.getClient().create(AuthApi.class);//Create API Service
        RegisterRequest request = new RegisterRequest(email, name, password);//Create Request Object
        Call<RegisterResponse> call = authApi.register(request);//Make Call
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {//When Success
                    Log.d("REGISTER", new Gson().toJson(response.body()));//Log to Debug
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Đăng ký thành công");
                    builder.setMessage("Đăng ký thành công, hãy quay về trang đăng nhập");
                    builder.setPositiveButton("OK",(dialog, which) -> {
                        startActivity(intent);
                    }).show();
                }
                else{
                    handleErrorResponse(response);
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                txtError.setText("Network error: " + t.getMessage());
                txtError.setVisibility(View.VISIBLE);
                hideErrorAfterDelay();
            }
        });
    }

    private void hideErrorAfterDelay() {
        new android.os.Handler().postDelayed(() -> {
            txtError.setVisibility(View.GONE);
        }, 10000);
    }

    private void loadCaptcha() {
        Bitmap bitmap = captchaGenerator.createCaptchaBitmap();
        captchaImage.setImageBitmap(bitmap);
        captchaInput.setText("");
    }

    private void handleErrorResponse(Response<RegisterResponse> response) {
        try {
            if (response.errorBody() != null) {
                String errorJson = response.errorBody().string();
                Log.d("Register Error:",errorJson);
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
}

