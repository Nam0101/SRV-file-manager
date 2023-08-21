package com.example.srvfilemanager.ultils;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class ExtensionFileFilter implements FileFilter {
    private static final String TAG = "ExtensionFileFilter";
    private List<String> allowedExtensions;
    private static final String[] documentExtensions = new String[]{"doc", "docx", "pdf", "txt", "ppt", "pptx", "xls", "xlsx"};
    private static final String[] videoExtensions = new String[]{"mp4", "mkv", "avi", "flv", "wmv", "3gp", "mov"};
    private static final String[] imageExtensions = new String[]{"jpg", "jpeg", "png", "gif", "bmp", "webp", "tiff", "psd", "heif", "svg"};
    private static final String[] audioExtensions = new String[]{"mp3", "wav", "wma", "aac", "flac", "m4a", "ogg", "oga", "opus", "webm"};
    private static final int DOCUMENTS = 0;
    private static final int IMAGES = 1;
    private static final int VIDEOS = 2;
    private static final int MUSIC = 3;

    public ExtensionFileFilter(int type) {
        Log.i(TAG, "ExtensionFileFilter: " + type);
        switch (type) {
            case IMAGES:
                allowedExtensions = Arrays.asList(imageExtensions);
                break;
            case VIDEOS:
                allowedExtensions = Arrays.asList(videoExtensions);
                break;
            case MUSIC:
                allowedExtensions = Arrays.asList(audioExtensions);
                break;
            default:
                allowedExtensions = Arrays.asList(documentExtensions);
                break;
        }

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

