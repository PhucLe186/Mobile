package com.example.projectmobile.ApiConfig;

import com.example.projectmobile.Test;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface keyAPI {
    @GET("test")
    Call<List<Test>> getTest();
}
