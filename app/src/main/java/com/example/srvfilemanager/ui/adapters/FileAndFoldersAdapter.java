package com.example.srvfilemanager.ui.adapters;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.srvfilemanager.R;
import com.example.srvfilemanager.databinding.FolderSingleItemBinding;
import com.example.srvfilemanager.diffutil.FileAndFolderDiffUtil;
import com.example.srvfilemanager.ui.FolderActivity;
import com.example.srvfilemanager.viewmodels.FilesAndFoldersListViewModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
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
                showFolderPopupMenu(selectedFile, v,position);
            } else {
                showFilePopupMenu(selectedFile, v, position);
            }
            return false;
        });
    }

    private void showFolderPopupMenu(File selectedFile, View v,int position) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenu().add("Rename");
        popupMenu.getMenu().add("Copy");
        popupMenu.getMenu().add("Cut");
        popupMenu.getMenu().add("Zip");
        popupMenu.getMenu().add("Delete");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getTitle().equals("Delete")) {
                handleDeleteFolder(selectedFile, v, position);
            }
            return false;
        });
    }

    private void showFilePopupMenu(File selectedFile, View v, int position) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenu().add("Rename");
        popupMenu.getMenu().add("Copy");
        popupMenu.getMenu().add("Cut");
        popupMenu.getMenu().add("Delete");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getTitle().equals("Delete")) {
                handleDeleteFile(selectedFile, v, position);
            }
            if (menuItem.getTitle().equals("Rename")) {
                handleRenameFile(selectedFile, v, position);
            }
            if (menuItem.getTitle().equals("Copy")) {
                handleCopyFile(selectedFile, v, position);
            }
            return false;

        });

    }

    private void handleCopyFile(File selectedFile, View v, int position) {
        Intent intent = new Intent(v.getContext(), FolderActivity.class);
        intent.putExtra("copy", selectedFile.getPath());
        v.getContext().startActivity(intent);
    }

//    private void handleCopyFile(File selectedFile, View v, int position,String newPath) {
//
//    }

    private void handleDeleteFile(File selectedFile, View v, int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        dialog.setTitle("Delete " + selectedFile.getName());
        dialog.setCancelable(true);
        dialog.setPositiveButton("Delete", (dialogInterface, i) -> {
            if(selectedFile.delete()){
                Log.i("Delete", "handleDeleteFile: " + selectedFile.getName());
                Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                filesAndFolders.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, filesAndFolders.size());
            }
            else {
                Toast.makeText(v.getContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    private void handleDeleteFolder(File selectedFile, View v, int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        dialog.setTitle("Delete " + selectedFile.getName());
        dialog.setCancelable(true);
        dialog.setPositiveButton("Delete", (dialogInterface, i) -> {
            try {
                FileUtils.deleteDirectory(selectedFile);
                Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                selectedFile.delete();
                v.setVisibility(View.GONE);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, filesAndFolders.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        dialog.show();
    }
    private void handleRenameFile(File selectedFile, View v, int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        dialog.setTitle("Enter new name for " + selectedFile.getName());
        dialog.setCancelable(true);
        final EditText editText = new EditText(v.getContext());
        dialog.setView(editText);
        dialog.setPositiveButton("Rename", (dialogInterface, i) -> {
            String mimeType = FilenameUtils.getExtension(selectedFile.getName());
            File newNameFile = new File(selectedFile.getParent() + "/" + editText.getText().toString() + "." + mimeType);
            selectedFile.renameTo(newNameFile);
            Log.d("Rename", selectedFile.getName());
            Log.d("Rename to", selectedFile.getAbsolutePath());
            filesAndFolders.set(position, newNameFile);
            updateListItem(position, v, editText.getText().toString());
            Toast.makeText(v.getContext(), "Renamed to "+ newNameFile.getName(), Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }
    private void handleRenameFolder(File selectedFile, View v, int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        dialog.setTitle("Enter new name for " + selectedFile.getName());
        dialog.setCancelable(true);
        final EditText editText = new EditText(v.getContext());
        dialog.setView(editText);
        dialog.setPositiveButton("Rename", (dialogInterface, i) -> {
            File newNameFile = new File(selectedFile.getParent() + "/" + editText.getText().toString());
            selectedFile.renameTo(newNameFile);
            Log.d("Rename", selectedFile.getName());
            Log.d("Rename to", selectedFile.getAbsolutePath());
            filesAndFolders.set(position, newNameFile);
            updateListItem(position, v, editText.getText().toString());
            Toast.makeText(v.getContext(), "Renamed to "+ newNameFile.getName(), Toast.LENGTH_SHORT).show();
        });
        dialog.show();
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

    private void updateListItem(int position, @NonNull View v, String fileName) {
        TextView textView = v.findViewById(R.id.text_view_name);
        textView.setVisibility(View.VISIBLE);
        textView.setText(fileName);
        notifyItemChanged(position);
    }
}