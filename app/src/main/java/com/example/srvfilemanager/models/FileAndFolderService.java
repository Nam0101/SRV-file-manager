package com.example.srvfilemanager.models;

import java.io.File;

public class FileAndFolderService {

    private File[] fileAndFolders;
    private File root;
    private String path;

    public FileAndFolderService(String path) {
        this.path = path;
        this.root = new File(path);
        this.fileAndFolders = root.listFiles();
    }

    public File[] getFileAndFolders() {
        return fileAndFolders;
    }
    public File getRoot() {
        return root;
    }

    public String getPath() {
        return path;
    }
    public int getNumberOfFiles() {
        return fileAndFolders.length;
    }
    public int getImageResource() {
        if (root.isDirectory()) {
            return android.R.drawable.ic_menu_gallery;
        } else {
            return android.R.drawable.ic_menu_camera;
        }
    }
    public int getTotalStorage() {
        return 1;
    }

}
