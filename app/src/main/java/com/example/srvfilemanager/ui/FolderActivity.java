package com.example.srvfilemanager.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FolderActivity extends AppCompatActivity {
    String TAG = "FolderActivity";
    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    FilesAndFoldersListViewModel filesAndFoldersListViewModel;
    ActivityFolderBinding binding;
    RecyclerView recyclerView;
    ExtensionFileFilter filter;
    String folderName;
    static String cutPath, copyPath;
    ImageButton btnPaste, btnCancel;
    ImageButton mAddFolderButton, mPasteButton, mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        binding = ActivityFolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAddFolderButton = findViewById(R.id.btn_add_folder);
        if (getIntent().hasExtra("filter")) {
            mAddFolderButton.setVisibility(View.GONE);
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

        } else if (getIntent().hasExtra("path")) {
            Log.i(TAG, "onCreate: has path");
            folderName = getIntent().getStringExtra("folderName");
            path = getIntent().getStringExtra("path");
            filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(path, folderName);
        }

        if (getIntent().hasExtra("cut")) {
            cutPath = getIntent().getStringExtra("cut");
        } else if (getIntent().hasExtra("copy")) {
            copyPath = getIntent().getStringExtra("copy");
        }
        btnPaste = findViewById(R.id.paste);
        btnCancel = findViewById(R.id.cancel);
        if (copyPath != null) {
            handleCopy();
        }
        if (cutPath != null) {
            handleCut();
        }
        binding.setFilesAndFoldersListViewModel(filesAndFoldersListViewModel);
        binding.setLifecycleOwner(this);
        recyclerView = binding.folderList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filesAndFoldersListViewModel.getAdapter());
        mAddFolderButton.setOnClickListener(view -> {
            Log.i(TAG, "onCreate: click add folder");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add new folder");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String folderName = input.getText().toString();
                filesAndFoldersListViewModel.addFolder(path, folderName);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: " + path);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume:" + path);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: " + path);
        filesAndFoldersListViewModel.scanPathUpdate(path);
        if (cutPath == null) {
            btnPaste.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }
    }

    private void handleCut() {
        btnCancel.setVisibility(View.VISIBLE);
        btnPaste.setVisibility(View.VISIBLE);
        btnCancel.setOnClickListener(view -> {
            cutPath = null;
            btnCancel.setVisibility(View.GONE);
            btnPaste.setVisibility(View.GONE);
        });
        btnPaste.setOnClickListener(view -> {
            btnCancel.setVisibility(View.GONE);
            btnPaste.setVisibility(View.GONE);
            File newFile = new File(cutPath);
            String newPath = path + "/" + newFile.getName();
            File copyFile = new File(newPath);
            try {
                Files.move(newFile.toPath(), copyFile.toPath());
            } catch (IOException e) {
                Log.i(TAG, "Can not cut file");
            }
            cutPath = null;
        });
    }

    private void handleCopy() {
        btnCancel.setVisibility(View.VISIBLE);
        btnPaste.setVisibility(View.VISIBLE);
        btnCancel.setOnClickListener(view -> {
            copyPath = null;
            btnCancel.setVisibility(View.GONE);
            btnPaste.setVisibility(View.GONE);
        });
        btnPaste.setOnClickListener(view -> {
            btnCancel.setVisibility(View.GONE);
            btnPaste.setVisibility(View.GONE);
            File newFile = new File(copyPath);
            String newPath = path + "/" + newFile.getName();
            File copyFile = new File(newPath);
            if (copyFile.exists()) {
                Toast.makeText(this, "File already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newFile.isDirectory()) {
                Log.i(TAG, "handleCopy: " + copyFile.getAbsolutePath());
                copyFolder(newFile, copyFile);
                copyPath = null;
                return;
            }
            try {
                Files.copy(newFile.toPath(), copyFile.toPath());
            } catch (IOException e) {
                Log.i(TAG, "Can not cut file");
            }
            copyPath = null;
        });
    }

    private void copyFolder(File sourceFolder, File targetFolder) {
        try {
            Files.copy(sourceFolder.toPath(), targetFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (sourceFolder.isDirectory()) {
            File[] files = sourceFolder.listFiles();
            for (File file : files) {
                File copiedFile = new File(targetFolder.getAbsoluteFile() + "/" + file.getName());
                Log.i("copy", "copyFolder: " + copiedFile.getAbsolutePath());
                copyFolder(file, copiedFile);
            }
        }
    }
}