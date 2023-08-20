package com.example.srvfilemanager.models;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileAndFolderService {
    private static final String items = " items | ";
    private static final String mB = " MB";
    private static final String kB = " kB";
    private static final String gB = " GB";
    private List<File> filesAndFolders;
    private File root;
    private int imageResource;

    public FileAndFolderService(String path) {
        this.root = new File(path);
        Log.d("FileAndFolderService", root.getName());
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            if (files != null) {
                this.filesAndFolders = Arrays.asList(files);
            } else {
                this.filesAndFolders = new ArrayList<>();
            }
        } else {
            this.filesAndFolders = Arrays.asList(root);
        }
        List<File> pdfFiles = getPdfFiles();
        for (File file : pdfFiles) {
            Log.d("FileAndFolderService with PDF", file.getName());
        }
//        for(File file : filesAndFolders) {
//            Log.d("FileAndFolderService", file.getName());
//        }

    }
    public List<File> getPdfFiles() {
        List<File> pdfFiles = new ArrayList<>();
        for (File file : filesAndFolders) {
            if (file.isFile() && file.getName().endsWith(".pdf")) {
                pdfFiles.add(file);
            }
        }
        return pdfFiles;
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
            long size = 0;
            for (File file : Objects.requireNonNull(root.listFiles())) {
                size += file.length();
            }
            if (size < 1024) {
                return size + kB;
            } else if (size < 1024 * 1024) {
                return size / 1024 + mB;
            } else {
                return size / (1024 * 1024) + gB;
            }
        } else {
            long size = root.length();
            if (size < 1024) {
                return size + kB;
            } else if (size < 1024 * 1024) {
                return size / 1024 + mB;
            } else {
                return size / (1024 * 1024) + gB;
            }
        }
    }

    public File getFileRoot() {
        return root;
    }
}
