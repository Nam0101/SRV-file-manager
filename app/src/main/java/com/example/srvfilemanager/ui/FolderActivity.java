package com.example.srvfilemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;

import com.example.srvfilemanager.R;
import com.example.srvfilemanager.databinding.ActivityFolderBinding;
import com.example.srvfilemanager.ui.adapters.FileAndFoldersAdapter;
import com.example.srvfilemanager.viewmodels.FilesAndFoldersListViewModel;

import java.io.File;

public class FolderActivity extends AppCompatActivity {

    String path = Environment.getExternalStorageDirectory().getPath();
    RecyclerView recyclerView ;
    File[] files;
    FilesAndFoldersListViewModel filesAndFoldersListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        recyclerView = findViewById(R.id.folderList);
        files = new File(path).listFiles();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new FileAndFoldersAdapter(files));
//        ActivityFolderBinding binding = ActivityFolderBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(path);
//        binding.setFilesAndFoldersListViewModel(filesAndFoldersListViewModel);
//        binding.setLifecycleOwner(this);

    }
}