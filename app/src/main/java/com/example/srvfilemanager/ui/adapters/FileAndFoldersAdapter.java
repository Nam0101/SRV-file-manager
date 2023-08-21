package com.example.srvfilemanager.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.srvfilemanager.databinding.FolderSingleItemBinding;
import com.example.srvfilemanager.diffutil.FileAndFolderDiffUtil;
import com.example.srvfilemanager.viewmodels.FilesAndFoldersListViewModel;

import java.io.File;
import java.util.List;

public class FileAndFoldersAdapter extends RecyclerView.Adapter<FileAndFoldersAdapter.FileViewHolder> {
    private List<File> filesAndFolders;

    public FileAndFoldersAdapter(List<File> filesAndFolders) {
        this.filesAndFolders = filesAndFolders;
    }

    @NonNull
    @Override
    public FileAndFoldersAdapter.FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FolderSingleItemBinding folderSingleItemBinding = FolderSingleItemBinding.inflate(layoutInflater, parent, false);
        return new FileViewHolder(folderSingleItemBinding);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull FileAndFoldersAdapter.FileViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.folderSingleItemBinding.unbind();
    }

    @Override
    public void onBindViewHolder(@NonNull FileAndFoldersAdapter.FileViewHolder holder, int position) {
        File selectedFile = filesAndFolders.get(position);
        FilesAndFoldersListViewModel filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(selectedFile.getPath());
        holder.folderSingleItemBinding.setFilesAndFoldersListViewModel(filesAndFoldersListViewModel);
        holder.folderSingleItemBinding.executePendingBindings();
        holder.folderSingleItemBinding.getRoot().setOnClickListener(v -> {
            if (selectedFile.isDirectory()) {
                filesAndFoldersListViewModel.openFolder(v.getContext(), selectedFile);
                Log.i("FileAndFoldersAdapter", "onBindViewHolder: " + selectedFile.getName());
            } else {
                filesAndFoldersListViewModel.openFile(v.getContext(), selectedFile);
                Log.i("FileAndFoldersAdapter", "onBindViewHolder: " + selectedFile.getName());
            }
        });
        holder.folderSingleItemBinding.getRoot().setOnLongClickListener(v -> {
            if (selectedFile.isDirectory()) {
                filesAndFoldersListViewModel.showFolderPopupMenu(v.getContext(), selectedFile);
          } else {
               filesAndFoldersListViewModel.showFilePopupMenu(v.getContext(), selectedFile);
            }
            return false;
        });
    }

    @Override
    public void onViewRecycled(@NonNull FileAndFoldersAdapter.FileViewHolder holder) {
        super.onViewRecycled(holder);
        holder.folderSingleItemBinding.unbind();
    }

    @Override
    public int getItemCount() {
        return filesAndFolders.size();
    }

    public void setFileList(List<File> fileList) {
        this.filesAndFolders = fileList;
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        FolderSingleItemBinding folderSingleItemBinding;

        public FileViewHolder(FolderSingleItemBinding itemView) {
            super(itemView.getRoot());
            this.folderSingleItemBinding = itemView;
        }
    }

    public void updateList(List<File> newList) {
        FileAndFolderDiffUtil fileAndFolderDiffUtil = new FileAndFolderDiffUtil(filesAndFolders, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(fileAndFolderDiffUtil);
        filesAndFolders.clear();
        filesAndFolders.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

}
