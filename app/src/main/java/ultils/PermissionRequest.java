package ultils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.AccessController;

public class PermissionRequest {

    public static final int REQUEST_CODE = 1;
    private static final String TAG = "PermissionRequest";

    public static final String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void requestPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, REQUEST_CODE);
        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, PERMISSIONS[0]) ||
                ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, PERMISSIONS[1])
        ) {
            Toast.makeText(context, "Permission is needed to access files", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
