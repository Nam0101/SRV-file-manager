package com.example.srvfilemanager.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.example.srvfilemanager.models.FileAndFolderService;
import com.example.srvfilemanager.ui.adapters.FileAndFoldersAdapter;

import java.io.File;
import java.nio.file.Files;


//public class FilesAndFoldersListViewModel extends BaseObservable {
//
//    FileAndFolderService data;
//    private FileAndFoldersAdapter adapter;
//
//    public FilesAndFoldersListViewModel(String path) {
//        super();
//        this.data = new FileAndFolderService(path);
//        this.adapter = new FileAndFoldersAdapter(this.data.getFileAndFolders());
//    }
//    @Bindable
//    public FileAndFoldersAdapter getAdapter() {
//        return adapter;
//    }
//    @Bindable
//    public String getRootName() {
//        return data.getPath();
//    }
//    @Bindable
//    public int getNumberOfFiles() {
//        return data.getNumberOfFiles();
//    }
//    @Bindable
//    public int getTotalStorage() {
//        return data.getTotalStorage();
//    }
//
//    public void setData(FileAndFolderService data) {
//        this.data = data;
//    }
//    @Bindable
//    public FileAndFolderService getData() {
//        return this.data;
//    }
//}
public class FilesAndFoldersListViewModel {
    private String rootName;
    private FileAndFoldersAdapter adapter;

    public FilesAndFoldersListViewModel(String rootName) {
        this.rootName = rootName;
        this.adapter = new FileAndFoldersAdapter(new File(rootName).listFiles());
    }

    public String getRootName() {
        return rootName;
    }

    public FileAndFoldersAdapter getAdapter() {
        return adapter;
    }
}
