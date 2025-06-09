package com.example.projectmobile;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnSearch;
    private FrameLayout btnAdd;
    private ImageView btnUser, btnInbox, btnHome;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnSearch=findViewById(R.id.btn_search);
        btnAdd= findViewById(R.id.btn_add_video);
        btnHome = findViewById(R.id.img_home);
        btnInbox = findViewById(R.id.img_inbox);
        btnUser = findViewById(R.id.img_user_icon);


        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_inbox).setOnClickListener(this);
        findViewById(R.id.btn_user_icon).setOnClickListener(this);

        loadFragment(new VideoActivity());
        btnSearch.setOnClickListener(v -> {
            startActivity(new Intent( this, SearchActivity.class));
        });
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent( this, CreateVideoActivity.class));
        });
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        int id =v.getId();

        if(id == R.id.btn_user_icon){
            selectedFragment= new UserInformation_LoggedInProfile();
        } else if (id == R.id.btn_home) {
            selectedFragment = new VideoActivity();
        }else if (id == R.id.btn_inbox) {
            selectedFragment = new InboxActivity();
        }

        if (selectedFragment != null) {
            loadFragment(selectedFragment);
            updateMenuIconColor(id);
        }
    }
    private void updateMenuIconColor(int selectedId) {

        btnHome.setImageResource(R.drawable.home1);
        btnInbox.setImageResource(R.drawable.chat);
        btnUser.setImageResource(R.drawable.user1);

        if (selectedId == R.id.btn_home) {
            btnHome.setImageResource(R.drawable.home);
        } else if (selectedId == R.id.btn_inbox) {
            btnInbox.setImageResource(R.drawable.chat2);
        } else if (selectedId == R.id.btn_user_icon) {
            btnUser.setImageResource(R.drawable.user2);
        }
    }

}