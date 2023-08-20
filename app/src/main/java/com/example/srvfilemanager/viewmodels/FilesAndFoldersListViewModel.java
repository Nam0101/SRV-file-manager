package com.example.srvfilemanager.viewmodels;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.srvfilemanager.R;
import com.example.srvfilemanager.models.FileAndFolderService;
import com.example.srvfilemanager.ui.adapters.FileAndFoldersAdapter;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;

public class FilesAndFoldersListViewModel extends BaseObservable {
    FileAndFolderService data;
    private String src;
    private FileAndFoldersAdapter adapter;
    private List<File> fileList;
    private int imageUrl;

    public FilesAndFoldersListViewModel(String path) {
        super();
        this.data = new FileAndFolderService(path);
        this.fileList = data.getFilesAndFolders();
        this.adapter = new FileAndFoldersAdapter(fileList);
        notifyChange();
    }

    @Bindable
    public FileAndFoldersAdapter getAdapter() {
        return adapter;
    }

    @Bindable
    public String getRootName() {
        return data.getFileName();
    }

    @Bindable
    public String getNumberOfFiles() {
        return data.getNumberOfFiles();
    }

    @Bindable
    public String getTotalStorage() {
        return data.getTotalStorage();
    }

    @Bindable
    public FileAndFolderService getData() {
        return this.data;
    }

    public void setData(FileAndFolderService data) {
        this.data = data;
    }

    @Bindable
    public List<File> getFileList() {
        return fileList;
    }

    @Bindable
    public int getImageUrl() {
        File file = data.getFileRoot();
        String mimeType = FilenameUtils.getExtension(file.getName());
        if (file.isDirectory()) {
            return R.mipmap.ic_folder;
        }
        Log.d("mimeType", file.getName());
        Log.d("mimeType", mimeType);
        Log.d("mimeType", file.getAbsolutePath());
        switch (mimeType) {
            case "jpg":
            case "jpeg":
            case "png":
                return R.mipmap.ic_picture;
            case "mp3":
            case "wav":
            case "mp4":
            case "avi":
            case "mkv":
                return R.mipmap.ic_mucsic;
            case "pdf":
                return R.mipmap.ic_pdf;
        }
        return R.mipmap.ic_add;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
        notifyChange();
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
        notifyChange();
    }
}
