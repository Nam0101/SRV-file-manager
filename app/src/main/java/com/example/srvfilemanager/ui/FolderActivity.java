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
import java.util.ArrayList;
import java.util.List;

import ultils.ExtensionFileFilter;

public class FolderActivity extends AppCompatActivity {
    String TAG = "FolderActivity";
    String path;
    FilesAndFoldersListViewModel filesAndFoldersListViewModel;
    ActivityFolderBinding binding;
    RecyclerView recyclerView;
    String filterString;
    ExtensionFileFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        binding = ActivityFolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent().hasExtra("filter")) {
            filterString = getIntent().getStringExtra("filter");
            Log.i(TAG, "onCreate: " + filterString);
            filter = new ExtensionFileFilter();
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(path, filter);
            for(File file:filesAndFoldersListViewModel.getFileList()){
                Log.i(TAG, "onCreate: "+file.getName());
            }
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