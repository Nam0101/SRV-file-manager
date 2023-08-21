package com.example.srvfilemanager.viewmodels;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.srvfilemanager.R;
import com.example.srvfilemanager.models.FileAndFolderService;
import com.example.srvfilemanager.ui.FolderActivity;
import com.example.srvfilemanager.ui.adapters.FileAndFoldersAdapter;
import com.example.srvfilemanager.ultils.ExtensionFileFilter;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesAndFoldersListViewModel extends BaseObservable {

    FileAndFolderService data;
    private FileAndFoldersAdapter adapter;
    private List<File> fileList;
    private int imageUrl;
    ImageButton menuImageButton;
    ExtensionFileFilter extensionFileFilter;
    String folderName;

    public FilesAndFoldersListViewModel(String path) {
        super();
        this.data = new FileAndFolderService(path);
        this.fileList = data.getFilesAndFolders();
        this.adapter = new FileAndFoldersAdapter(fileList);
        notifyChange();
    }

    public FilesAndFoldersListViewModel(String path, ExtensionFileFilter extensionFileFilter, String folderName) {
        super();
        this.folderName = folderName;
        this.extensionFileFilter = extensionFileFilter;
        this.data = new FileAndFolderService(path);
        this.fileList = filterFileList();
        this.adapter = new FileAndFoldersAdapter(fileList);
    }

    public FilesAndFoldersListViewModel(List<File> fileList, ExtensionFileFilter extensionFileFilter, String folderName) {
        super();
        this.folderName = folderName;
        this.extensionFileFilter = extensionFileFilter;
        this.fileList = fileList(fileList);
        this.adapter = new FileAndFoldersAdapter(this.fileList);
    }

    public FilesAndFoldersListViewModel(String path, String folderName) {
        super();
        this.folderName = folderName;
        this.data = new FileAndFolderService(path);
        this.fileList = data.getFilesAndFolders();
        this.adapter = new FileAndFoldersAdapter(fileList);
    }

    public List<File> filterFileList() {
        List<File> filteredFileList = new ArrayList<>();
        List<File> fileList = data.getFilesAndFolders();
        for (File file : fileList) {
            if (extensionFileFilter.accept(file)) {
                filteredFileList.add(file);
            }
        }
        return filteredFileList;
    }

    public List<File> fileList(List<File> list) {
        List<File> filteredFileList = new ArrayList<>();
        for (File file : list) {
            if (extensionFileFilter.accept(file)) {
                filteredFileList.add(file);
            }
        }
        return filteredFileList;
    }

    @Bindable
    public FileAndFoldersAdapter getAdapter() {
        return adapter;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Bindable
    public boolean isListEmpty() {
        return fileList.isEmpty();
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
                return R.mipmap.ic_jpg;
            case "jpeg":
            case "png":
                return R.mipmap.ic_png;
            case "gif":
            case "bmp":
                return R.mipmap.ic_picture;
            case "svg":
                return R.mipmap.ic_svg;
            case "mp3":
                return R.mipmap.ic_mp3;
            case "mp4":
                return R.mipmap.ic_mp4;
            case "avi":
            case "wav":
            case "mkv":
            case "flv":
            case "mov":
            case "oog":
                return R.mipmap.ic_mucsic;
            case "pdf":
                return R.mipmap.ic_pdf;
        }
        return R.mipmap.ic_file;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
        notifyChange();
    }

    public void setFileList(List<File> fileList) {
        data.setFilesAndFolders(fileList);
        this.fileList = fileList;
        notifyChange();
    }

    public void openFolder(Context context, File selectedFile) {
        try {
            FilesAndFoldersListViewModel filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(selectedFile.getAbsolutePath());
            adapter.setFileList(filesAndFoldersListViewModel.getFileList());
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(context, FolderActivity.class);
            intent.putExtra("path", selectedFile.getAbsolutePath());
            intent.putExtra("folderName", selectedFile.getName());
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Can't open folder", Toast.LENGTH_SHORT).show();
        }
    }

    public void openFile(Context context, File selectedFile) {
        try {
            Uri uri = FileProvider.getUriForFile(context, "com.example.srvfilemanager.fileprovider", selectedFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent chooser = Intent.createChooser(intent, "Open file with");
            context.startActivity(chooser);
        } catch (Exception e) {
            Log.e("error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(context, "Can't open file", Toast.LENGTH_SHORT).show();
        }
    }

    public void showFolderPopupMenu(Context context, File selectedFile) {
        MenuInflater inflater = new MenuInflater(context);

    }

    public void showFilePopupMenu(Context context, File selectedFile) {
    }
}