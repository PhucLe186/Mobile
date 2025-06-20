package com.example.projectmobile.Information;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectmobile.Auth.LoginActivity;
import com.example.projectmobile.R;

public class GuestProfileActivity extends Fragment {

    private Button loginButton;
    public GuestProfileActivity() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_information_guest, container, false);

        initView(view);
        initListener();

        return view;
    }
    private void initView(View view) {
        loginButton = view.findViewById(R.id.button3);
    }
    private void initListener() {
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}
