package com.example.srvfilemanager.ultils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

@RequiresApi(api = Build.VERSION_CODES.R)
public class PermissionRequest {

    public static final int REQUEST_CODE = 1;
    private static final String TAG = "PermissionRequest";

    public static final String[] PERMISSIONS = {
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void requestPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, REQUEST_CODE);
        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, PERMISSIONS[0])
        ) {
            Toast.makeText(context, "Permission is needed to access files", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, PERMISSIONS[1]) ;
        int result2 = ContextCompat.checkSelfPermission(context, PERMISSIONS[2]);
        return result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }
}