package com.example.srvfilemanager.models;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileAndFolderService {
    private static final String items = " items | ";
    private static final String B = " B";
    private static final String mB = " MB";
    private static final String kB = " kB";
    private static final String gB = " GB";
    private List<File> filesAndFolders;
    private File root;


    public FileAndFolderService(String path) {
        this.root = new File(path);
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            if (files != null) {
                this.filesAndFolders = Arrays.asList(files);
            } else {
                this.filesAndFolders = new ArrayList<>();
            }
        } else {
            this.filesAndFolders = Collections.singletonList(root);
        }
        for (File file : filesAndFolders) {
            Log.d("FileAndFolderService", file.getAbsolutePath());
        }

    }

    public String getFileName() {
        return root.getName();
    }

    public List<File> getFilesAndFolders() {
        return filesAndFolders;
    }

    public String getNumberOfFiles() {
        if (root.isDirectory()) {
            return Objects.requireNonNull(root.listFiles()).length + items;
        } else {
            return "";
        }
    }

    public String getTotalStorage() {
        if (root.isDirectory()) {
            long fileSize = 0;
            for (File file : Objects.requireNonNull(root.listFiles())) {
                fileSize += file.length();
            }
            return getTotalFileSize(fileSize);
        } else {
            long size = root.length();
            return getTotalFileSize(size);
        }
    }
    private String getTotalFileSize(long size){
        if (size < 1000) {
            return size + B;
        } else if (size < 1000 * 1000) {
            return size / 1000 + kB;
        } else if(size < 1000 * 1000 * 1000){
            return size / (1000 * 1000) + mB;
        }
        else {
            return size / (1000 * 1000 * 1000) + gB;
        }
    }

    public File getFileRoot() {
        return root;
    }

    public void setFilesAndFolders(List<File> fileList) {
        this.filesAndFolders = fileList;
    }
}
