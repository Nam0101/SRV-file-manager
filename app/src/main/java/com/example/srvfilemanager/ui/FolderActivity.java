package com.example.srvfilemanager.ui;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.srvfilemanager.R;
import com.example.srvfilemanager.databinding.ActivityFolderBinding;
import com.example.srvfilemanager.viewmodels.FilesAndFoldersListViewModel;

import java.io.File;
import java.util.List;

import ultils.AllFileInStorage;
import ultils.ExtensionFileFilter;

public class FolderActivity extends AppCompatActivity {
    private static final int DOCUMENTS = 0;
    private static final int IMAGES = 1;
    private static final int VIDEOS = 2;
    private static final int MUSIC = 3;
    String TAG = "FolderActivity";
    String path;
    FilesAndFoldersListViewModel filesAndFoldersListViewModel;
    ActivityFolderBinding binding;
    RecyclerView recyclerView;
    ExtensionFileFilter filter;
    String folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        binding = ActivityFolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent().hasExtra("filter")) {
            int filterType = getIntent().getIntExtra("filter", 0);
            Log.i(TAG, "onCreate: " + filterType);
            filter = new ExtensionFileFilter(filterType);
            folderName = getIntent().getStringExtra("folderName");
            AllFileInStorage allFileInStorage = new AllFileInStorage();
            allFileInStorage.getAllFilesInStorage();
            List<File> allFiles = allFileInStorage.getFileList();
            for (File file : allFiles) {
                Log.i(TAG, "File in storage: " + file.getName());
            }
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(allFiles, filter, folderName);
        } else {
            Log.i(TAG, "onCreate: no filter");
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(path);
            for(File file:filesAndFoldersListViewModel.getFileList()){
                Log.i(TAG, "onCreate: "+file.getName());
            }
        }
        binding.setFilesAndFoldersListViewModel(filesAndFoldersListViewModel);
        binding.setLifecycleOwner(this);
        recyclerView = binding.folderList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filesAndFoldersListViewModel.getAdapter());

    }
}