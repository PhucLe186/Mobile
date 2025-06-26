package com.example.projectmobile.Auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthApi;
import com.example.projectmobile.Auth.AuthModel.ChangePassReq;
import com.example.projectmobile.Auth.AuthModel.ChangePassRes;
import com.example.projectmobile.R;
import com.example.projectmobile.Setting.SettingActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText oldPass, newPass, renewPass;
    private ImageView backImgBtn;
    private Button sendBtn;
    private AuthApi api;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        oldPass = findViewById(R.id.edit_oldpass);
        newPass = findViewById(R.id.edit_newpass);
        renewPass = findViewById(R.id.edit_renewpass);
        backImgBtn = findViewById(R.id.imgBtn_back);
        sendBtn = findViewById(R.id.btn_send_newpass);

        sendBtn.setOnClickListener(v->{handleSendNewPassword(oldPass, newPass,renewPass);});
        createIntent(backImgBtn);
    }

    //handling Change Password API =D
    private void handleSendNewPassword(EditText oldpass, EditText newpass, EditText renewpass) {
        String oldpass_value = oldpass.getText().toString().trim();
        String newpass_value = newpass.getText().toString().trim();
        String renewpass_value = renewpass.getText().toString().trim();
        if(isValidate(oldpass_value,newpass_value,renewpass_value)){
            SharedPreferences preferences = getSharedPreferences("MyAppPrefs",MODE_PRIVATE);
            token = preferences.getString("token",null);
            api = ApiClient.getClient().create(AuthApi.class);
            api.requireChangePassword("Bearer "+token, new ChangePassReq(oldpass_value,newpass_value))
                .enqueue(new Callback<ChangePassRes>() {
                    @Override
                    public void onResponse(Call<ChangePassRes> call, Response<ChangePassRes> response) {
                        if(response.isSuccessful()){
                            Log.d("changepass",String.valueOf(response));
                            Toast.makeText(ChangePasswordActivity.this, "Your Password has been Changed!!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            try {
                                String errorBody = response.errorBody().string();
                                JSONObject jsonObject = new JSONObject(errorBody);
                                String message = jsonObject.optString("message", "Change password failed");
                                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(ChangePasswordActivity.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePassRes> call, Throwable t) {
                        Log.e("changepass", "Request failed", t);

                        String errorMessage;

                        if (t instanceof IOException) {
                            // Lỗi kết nối mạng, timeout
                            errorMessage = "Network error! Please check your internet connection.";
                        } else if (t instanceof HttpException) {
                            // Lỗi HTTP
                            errorMessage = "Unexpected server error!";
                        } else {
                            // Lỗi khác
                            errorMessage = "An unexpected error occurred!";
                        }

                        Toast.makeText(ChangePasswordActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
        }
    }

    //Back to setting when clicking on Back Button
    private void createIntent(ImageView backImgBtn) {
        backImgBtn.setOnClickListener(v->{
           finish();
        });
    }

    //Validation of Input on EditText
    private boolean isValidate(String oldpass_value, String newpass_value, String renewpass_value){
        if(oldpass_value.isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập mật khẩu cũ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(newpass_value.isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(renewpass_value.isEmpty()){
            Toast.makeText(this, "Bạn chưa xác nhận mật khẩu mới!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!renewpass_value.equals(newpass_value)){
            Toast.makeText(this,"Mật khẩu mới không trùng khớp",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}