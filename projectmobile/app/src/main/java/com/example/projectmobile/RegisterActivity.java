package com.example.projectmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmobile.Module.CaptchaGenerator;

public class RegisterActivity extends AppCompatActivity {

    ImageView captchaImage, back_btn;
    TextView loginBtn;
    EditText captchaInput;
    Button btnVerify;
    Button btnRefresh;

    CaptchaGenerator captchaGenerator;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        captchaImage = findViewById(R.id.captchaImage);
        captchaInput = findViewById(R.id.captchaInput);
        btnVerify = findViewById(R.id.btnVerify);
        btnRefresh = findViewById(R.id.btnRefresh);
        loginBtn = findViewById(R.id.btn_login);

        captchaGenerator = new CaptchaGenerator();

        loadCaptcha();

        btnRefresh.setOnClickListener(v -> {
            loadCaptcha();
        });

        btnVerify.setOnClickListener(v -> {
            String input = captchaInput.getText().toString().trim();
            if (input.equalsIgnoreCase(captchaGenerator.getCaptchaCode())) {
                Toast.makeText(this, "CAPTCHA đúng!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "CAPTCHA sai!", Toast.LENGTH_SHORT).show();
                loadCaptcha();
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

    private void loadCaptcha() {
        Bitmap bitmap = captchaGenerator.createCaptchaBitmap();
        captchaImage.setImageBitmap(bitmap);
        captchaInput.setText("");
    }
}

