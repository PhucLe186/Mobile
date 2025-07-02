package com.example.projectmobile.Information.User;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobile.ApiConfig.ApiClient;
import com.example.projectmobile.ApiConfig.AuthApi;
import com.example.projectmobile.ApiConfig.VideoApi;
import com.example.projectmobile.Information.EditInformation.ActivityEditAvata;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemReq;
import com.example.projectmobile.Information.ItemVideo.GetVideosItemRes;
import com.example.projectmobile.Information.ItemVideo.VideoItemHolder;
import com.example.projectmobile.Module.DecodeToken;
import com.example.projectmobile.R;
import com.example.projectmobile.Setting.SettingActivity;
import com.example.projectmobile.follow.FollowActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserInformation_LoggedInProfile extends Fragment {

    private LinearLayout layout1, layout2, layout3;
    private TextView usernameText, followersText,followingText , likesText;
    private ImageView menuButton,imgAvatar, edit;
    private RecyclerView recyclerView;
    private VideoGridAdapter adapter;
    private List<String> gridList = new ArrayList<>();//Store user video
    private VideoApi videoApi;
    private AuthApi authApi;
    private int user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_infomation_logged_in, container, false);
        initView(view);
        getUserIdFromToken();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));//set layout
        getUserVideo();//get video from server then store it into gridList
        getUserInfo();//get user info to display on screen
        menuButton.setOnClickListener(v -> showBottomMenu());

        return view;
    }
    private void initView( View view) {
        layout1=view.findViewById(R.id.layout1);
        layout2=view.findViewById(R.id.layout2);
        usernameText = view.findViewById(R.id.txt_username);
        followingText = view.findViewById(R.id.txt_followers_number);
        followersText=view.findViewById(R.id.txt_following_number);
        likesText = view.findViewById(R.id.txt_like_number);
        menuButton = view.findViewById(R.id.img_menu);
        imgAvatar = view.findViewById(R.id.img_avatar);
        recyclerView = view.findViewById(R.id.recyclerView);
        edit=view.findViewById(R.id.editor);
    }
    private void showBottomMenu() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_menu, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        bottomSheetView.findViewById(R.id.menu_tiktok_studio).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetView.findViewById(R.id.menu_so_du).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetView.findViewById(R.id.menu_qr).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetView.findViewById(R.id.menu_settings).setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), SettingActivity.class));
            bottomSheetDialog.dismiss();
        });
    }
    private void getUserInfo() {
        authApi = ApiClient.getClient().create(AuthApi.class);
        GetUserInfoReq request = new GetUserInfoReq(user_id);
        authApi.getUserInfo(request).enqueue(new Callback<GetUserInfoRes>() {
            @Override
            public void onResponse(@NonNull Call<GetUserInfoRes> call, @NonNull Response<GetUserInfoRes> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    usernameText.setText(response.body().getData().getUserName());
                    followingText.setText(response.body().getData().getNumber_of_following());
                    followersText.setText(response.body().getData().getNumber_of_followers());
                    likesText.setText(response.body().getData().getNumber_of_likes());
                    Glide.with(requireContext())
                            .load(response.body().getData().getAvatar_url())
                            .placeholder(R.drawable.user)
                            .circleCrop()
                            .error(R.drawable.user)
                            .into(imgAvatar);
                    layout1.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), FollowActivity.class);
                        intent.putExtra("following", "1");
                        intent.putExtra("name", response.body().getData().getUserName());
                        intent.putExtra("user_id", response.body().getData().getUser_id());
                        startActivityForResult(intent, 123);
                    });
                    layout2.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), FollowActivity.class);
                        intent.putExtra("following", "0");
                        intent.putExtra("name", response.body().getData().getUserName());
                        intent.putExtra("user_id", response.body().getData().getUser_id());
                        startActivityForResult(intent, 123);
                    });
                    edit.setOnClickListener(v -> {
                        Intent intent =new Intent(getContext(), ActivityEditAvata.class);
                        startActivityForResult(intent, 123);
                    });
                }
            }
            @Override
            public void onFailure(Call<GetUserInfoRes> call, Throwable t) {
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();    // Cập nhật thông tin user
        gridList.clear(); // Xóa danh sách video cũ để load lại
        getUserVideo();   // Cập nhật video user
    }

    private void getUserIdFromToken() {
        SharedPreferences prefs = requireContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = prefs.edit();
        String token = prefs.getString("token", null);
        assert token != null;
        user_id = Integer.parseInt(new DecodeToken().getUserIdFromToken(token));
    }//handling decode token to get user_id
    private void videoHandle() {
        //Handling Scroll Event
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    adapter.playVisibleVideos(recyclerView);
                }
            }
        });
        //Handle the first video when it's display on screen
        recyclerView.post(()->adapter.playVisibleVideos(recyclerView));
    }
    private void getUserVideo() {
        videoApi = ApiClient.getClient().create(VideoApi.class);
        GetVideosItemReq request = new GetVideosItemReq(user_id);
        videoApi.getUserVideo(request).enqueue(new Callback<GetVideosItemRes>() {
            @Override
            public void onResponse(@NonNull Call<GetVideosItemRes> call, @NonNull Response<GetVideosItemRes> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;//ensure response body is not null
                    List<VideoItemHolder> videoList = response.body().getData();
                    if(videoList != null && !videoList.isEmpty()){
                        gridList.clear();
                        for (VideoItemHolder video : videoList) {
                            gridList.add(video.getVideo_url());
                        }
                        if(adapter==null){
                            Log.d("getUserVideo", gridList.get(0));
                            adapter = new VideoGridAdapter(getContext(), gridList);
                            recyclerView.setAdapter(adapter);//set adapter
                            videoHandle();//Handling video when it's display on screen
                        }
                    }
                }
                else{//if fail in get user's video
                    Log.d("getUserVideo", String.valueOf(response.body()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetVideosItemRes> call, @NonNull Throwable t) {
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == getActivity().RESULT_OK) {
            if (data != null && data.getBooleanExtra("refresh", false)) {
                getUserInfo(); // gọi lại API để cập nhật lại số lượng follow/following
            }
        }
    }

}
