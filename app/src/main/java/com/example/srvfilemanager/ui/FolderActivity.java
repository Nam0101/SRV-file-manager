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
            for (File file : allFiles) {
                Log.i(TAG, "File in storage: " + file.getName());
            }
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(allFiles, filter, folderName);
        } else if (!getIntent().hasExtra("path")) {
            Log.i(TAG, "onCreate: no filter");
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(path);
            for (File file : filesAndFoldersListViewModel.getFileList()) {
                Log.i(TAG, "onCreate: " + file.getName());
            }
        } else {
            Log.i(TAG, "onCreate: has path");
            folderName = getIntent().getStringExtra("folderName");
            path = getIntent().getStringExtra("path");
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(path, folderName);
            for (File file : filesAndFoldersListViewModel.getFileList()) {
                Log.i(TAG, "onCreate: " + file.getName());
            }
        }
        binding.setFilesAndFoldersListViewModel(filesAndFoldersListViewModel);
        binding.setLifecycleOwner(this);
        recyclerView = binding.folderList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filesAndFoldersListViewModel.getAdapter());
        registerForContextMenu(recyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.popupmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        if (item.getItemId() == R.id.itemRename) {
            Toast.makeText(this, "Rename", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.itemDelete) {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.itemCopy) {
            Toast.makeText(this, "Copy", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.itemZip) {
            Toast.makeText(this, "Zip", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.itemCut) {
            Toast.makeText(this, "Cut", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


}