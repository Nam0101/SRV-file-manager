package com.example.srvfilemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;

import ultils.StorageHelper;


public class HomePageActivity extends AppCompatActivity {
    String TAG ="HomePageActivity";
    TextView mtvFreeStorage,mtvUsedStorage,mtvProgress;
    ProgressBar mProgressBar;
    ImageButton mHomeButton;
    StorageHelper storageHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_actitity);
        mtvFreeStorage = findViewById(R.id.tv_free_storage);
        mtvUsedStorage = findViewById(R.id.tv_used_storage);
        mProgressBar = findViewById(R.id.progress_bar);
        mtvProgress = findViewById(R.id.text_view_progress);
        mtvProgress = findViewById(R.id.text_view_progress);
        mHomeButton=findViewById(R.id.btnHome);
        setStorageInfo();
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Button clicked");
            }
        });
        Log.e(TAG,"Path: "+Environment.getExternalStorageDirectory().getPath());
        Log.i(TAG,"onCreate");
    }
    private void setStorageInfo(){
        storageHelper = new StorageHelper(Environment.getExternalStorageDirectory().getPath());
        mtvFreeStorage.setText(storageHelper.getAvailableStorage());
        mtvUsedStorage.setText(storageHelper.getUsedStorage());
        mProgressBar.setProgress(storageHelper.getPercent());
        String percent = ""+storageHelper.getPercent()+"%";
        mtvProgress.setText(percent);
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