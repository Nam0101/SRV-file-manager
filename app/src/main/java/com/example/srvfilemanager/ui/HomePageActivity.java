package com.example.srvfilemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.srvfilemanager.R;

import com.example.srvfilemanager.databinding.ActivityHomePageBinding;
import com.example.srvfilemanager.models.StorageHelper;
import com.example.srvfilemanager.viewmodels.HomePageViewModel;


public class HomePageActivity extends AppCompatActivity {
    String TAG ="HomePageActivity";
    ImageButton mHomeButton;
    HomePageViewModel homePageViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ActivityHomePageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
        homePageViewModel = new HomePageViewModel(Environment.getExternalStorageDirectory().getPath());
        binding.setHomePageViewModel(homePageViewModel);
        binding.setLifecycleOwner(this);
        mHomeButton = findViewById(R.id.btnHome);
        mHomeButton.setOnClickListener(view -> Log.i(TAG,"Button clicked"));
        Log.e(TAG,"Path: "+Environment.getExternalStorageDirectory().getPath());
        Log.i(TAG,"onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}