package com.example.srvfilemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import ultils.PermissionRequest;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    ImageButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.startButton);

        if (PermissionRequest.checkPermission(this)) {
            startHomePageActivity();
        } else {
            mButton.setOnClickListener(v -> {
                PermissionRequest.requestPermission(this);
                Log.i(TAG, "onCreate: Button Clicked");
            });
        }
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionRequest.REQUEST_CODE) {
            if (PermissionRequest.checkPermission(this)) {
                startHomePageActivity();
            }
        }
    }

    private void startHomePageActivity() {
        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
}
