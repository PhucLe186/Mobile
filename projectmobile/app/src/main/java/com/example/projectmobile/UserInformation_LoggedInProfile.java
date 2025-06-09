package com.example.projectmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class UserInformation_LoggedInProfile extends Fragment {

    private TextView usernameText, followersText, likesText;
    private ImageView menuButton;

    public UserInformation_LoggedInProfile() {
        // Constructor rỗng là bắt buộc
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_infomation_logged_in, container, false);

        usernameText = view.findViewById(R.id.usernameText);
        followersText = view.findViewById(R.id.followersText);
        likesText = view.findViewById(R.id.likesText);
        menuButton = view.findViewById(R.id.img);

        // Dữ liệu giả lập
        usernameText.setText("@user123");
        followersText.setText("Followers: 12.3K");
        likesText.setText("Likes: 48.7K");

        // Bắt sự kiện menu
        menuButton.setOnClickListener(v -> showBottomMenu());

        return view;
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
}
