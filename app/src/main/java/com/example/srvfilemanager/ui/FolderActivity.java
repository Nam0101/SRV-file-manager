package com.example.srvfilemanager.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
    ImageButton mAddFolderButton;
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
        mAddFolderButton = findViewById(R.id.btn_add_folder);
        mAddFolderButton.setOnClickListener(view -> {
            Log.i(TAG, "onCreate: click add folder");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add new folder");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String folderName = input.getText().toString();
                filesAndFoldersListViewModel.addFolder(path, folderName);//Toast.makeText(this, "Add folder successfully", Toast.LENGTH_SHORT).show();
// Toast.makeText(this, "Add folder failed", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
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
        filesAndFoldersListViewModel.refreshList();
    }
}