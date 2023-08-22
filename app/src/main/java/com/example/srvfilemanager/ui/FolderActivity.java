package com.example.srvfilemanager.ui;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.srvfilemanager.R;
import com.example.srvfilemanager.databinding.ActivityFolderBinding;
import com.example.srvfilemanager.ultils.AllFileInStorage;
import com.example.srvfilemanager.ultils.ExtensionFileFilter;
import com.example.srvfilemanager.viewmodels.FilesAndFoldersListViewModel;

import java.io.File;
import java.util.List;

public class FolderActivity extends AppCompatActivity {
    String TAG = "FolderActivity";
    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
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
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(allFiles, filter, folderName);
        } else if (!getIntent().hasExtra("path")) {
            Log.i(TAG, "onCreate: no filter");
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(path);

        } else {
            Log.i(TAG, "onCreate: has path");
            folderName = getIntent().getStringExtra("folderName");
            path = getIntent().getStringExtra("path");
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(path, folderName);
        }
        binding.setFilesAndFoldersListViewModel(filesAndFoldersListViewModel);
        binding.setLifecycleOwner(this);
        recyclerView = binding.folderList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filesAndFoldersListViewModel.getAdapter());
        registerForContextMenu(recyclerView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume:");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }
}