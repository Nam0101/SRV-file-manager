package ultils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class ExtensionFileFilter implements FileFilter {
    private static final String TAG = "ExtensionFileFilter";
    private List<String> allowedExtensions;

    public ExtensionFileFilter() {
        this.allowedExtensions = Arrays.asList("png", "jpg", "srt");
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        }
        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot != -1 && lastIndexOfDot < fileName.length() - 1) {
            String fileExtension = fileName.substring(lastIndexOfDot + 1);
            for (String extension : allowedExtensions) {
                if (fileExtension.equalsIgnoreCase(extension)) {
                    Log.i(TAG, "Accept: " + fileName);
                    return true;
                }
            }
        }
        return false;
    }
}

