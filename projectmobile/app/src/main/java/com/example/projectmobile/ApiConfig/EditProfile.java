package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Information.EditInformation.Model.Information;
import com.example.projectmobile.Information.EditInformation.Model.Name;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface EditProfile {
    @Multipart
    @POST("editprofile/avata")
    Call<Void> UploadImg(@Header("Authorization") String token, @Part MultipartBody.Part image);
    @POST("editprofile/name")
    Call<Void> EditName(@Header("Authorization") String token, @Body Name name);

    @POST("editprofile/")
    Call<List<Information>> getInfor(@Header("Authorization") String token);
}
