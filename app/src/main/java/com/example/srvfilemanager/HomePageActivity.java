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

public class HomePageActivity extends AppCompatActivity {
    String TAG ="HomePageActivity";
    TextView mtvDungLuong,mtvDungLuongDadung,mtvProgress;
    ProgressBar mProgressBar;
    ImageButton mHomeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_actitity);
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
        long bytesTotal =(long)stat.getTotalBytes();
        double megAvailable = (double)bytesAvailable / (1000 * 1000*1000);
        double megTotal = (double)bytesTotal/(1000*1000*1000);
        NumberFormat formatter = new DecimalFormat("#0.00");
        String stringDungLuong = ""+ formatter.format(megAvailable) +" GB";
        String stringDungLuongDaDung = ""+ formatter.format(megTotal-megAvailable) +" GB";
        mtvDungLuong=findViewById(R.id.tvDungLuongMay);
        mtvDungLuongDadung=findViewById(R.id.tvDungLuongDaDung);
        mtvDungLuong.setText(stringDungLuong);
        mtvDungLuongDadung.setText(stringDungLuongDaDung);
        mProgressBar = findViewById(R.id.progress_bar);
        int percent = (int)(100*(megTotal-megAvailable)/megTotal);
        mProgressBar.setProgress(percent);
        mtvProgress = findViewById(R.id.text_view_progress);
        mtvProgress.setText(""+percent+"%");
        mHomeButton=findViewById(R.id.btnHome);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Button clicked");
            }
        });
        Log.e(TAG,"Available GB : "+megAvailable);
        Log.e(TAG,"Total GB: "+ megTotal);
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