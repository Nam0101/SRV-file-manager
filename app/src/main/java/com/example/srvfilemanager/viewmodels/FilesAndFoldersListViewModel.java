package com.example.srvfilemanager.viewmodels;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.srvfilemanager.R;
import com.example.srvfilemanager.models.FileAndFolderService;
import com.example.srvfilemanager.ui.FolderActivity;
import com.example.srvfilemanager.ui.adapters.FileAndFoldersAdapter;
import com.example.srvfilemanager.ultils.ExtensionFileFilter;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesAndFoldersListViewModel extends BaseObservable {

    FileAndFolderService data;
    private FileAndFoldersAdapter adapter;
    private List<File> fileList;
    private int imageUrl;
    Context contex;
    ImageButton menuImageButton;
    ExtensionFileFilter extensionFileFilter;
    String folderName;

    public FilesAndFoldersListViewModel(String path) {
        super();
        this.data = new FileAndFolderService(path);
        this.fileList = data.getFilesAndFolders();
        this.adapter = new FileAndFoldersAdapter(fileList);
        notifyChange();
    }

    public FilesAndFoldersListViewModel(String path, ExtensionFileFilter extensionFileFilter, String folderName) {
        super();
        this.folderName = folderName;
        this.extensionFileFilter = extensionFileFilter;
        this.data = new FileAndFolderService(path);
        this.fileList = filterFileList();
        this.adapter = new FileAndFoldersAdapter(fileList);
    }

    public FilesAndFoldersListViewModel(List<File> fileList, ExtensionFileFilter extensionFileFilter, String folderName) {
        super();
        this.folderName = folderName;
        this.extensionFileFilter = extensionFileFilter;
        this.fileList = fileList(fileList);
        this.adapter = new FileAndFoldersAdapter(this.fileList);
    }

    public FilesAndFoldersListViewModel(String path, String folderName) {
        super();
        this.folderName = folderName;
        this.data = new FileAndFolderService(path);
        this.fileList = data.getFilesAndFolders();
        this.adapter = new FileAndFoldersAdapter(fileList);
    }

    public List<File> filterFileList() {
        List<File> filteredFileList = new ArrayList<>();
        List<File> fileList = data.getFilesAndFolders();
        for (File file : fileList) {
            if (extensionFileFilter.accept(file)) {
                filteredFileList.add(file);
            }
        }
        return filteredFileList;
    }

    public List<File> fileList(List<File> list) {
        List<File> filteredFileList = new ArrayList<>();
        for (File file : list) {
            if (extensionFileFilter.accept(file)) {
                filteredFileList.add(file);
            }
        }
        return filteredFileList;
    }

    @Bindable
    public FileAndFoldersAdapter getAdapter() {
        return adapter;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Bindable
    public boolean isListEmpty() {
        return fileList.isEmpty();
    }

    @Bindable
    public String getRootName() {
        return data.getFileName();
    }

    @Bindable
    public String getNumberOfFiles() {
        return data.getNumberOfFiles();
    }

    @Bindable
    public String getTotalStorage() {
        return data.getTotalStorage();
    }

    @Bindable
    public FileAndFolderService getData() {
        return this.data;
    }

    public void setData(FileAndFolderService data) {
        this.data = data;
    }

    @Bindable
    public List<File> getFileList() {
        return fileList;
    }

    @Bindable
    public int getImageUrl() {
        File file = data.getFileRoot();
        String mimeType = FilenameUtils.getExtension(file.getName());
        if (file.isDirectory()) {
            return R.mipmap.ic_folder;
        }
        switch (mimeType) {
            case "jpg":
                return R.mipmap.ic_jpg;
            case "jpeg":
            case "png":
                return R.mipmap.ic_png;
            case "gif":
            case "bmp":
                return R.mipmap.ic_picture;
            case "svg":
                return R.mipmap.ic_svg;
            case "mp3":
                return R.mipmap.ic_mp3;
            case "mp4":
                return R.mipmap.ic_mp4;
            case "avi":
            case "wav":
            case "mkv":
            case "flv":
            case "mov":
            case "oog":
                return R.mipmap.ic_mucsic;
            case "pdf":
                return R.mipmap.ic_pdf;
            case "pptx":
            case "ppt":
                return R.mipmap.ic_pptx;
            case "txt":
                return R.mipmap.ic_txt;
            case "xlsx":
            case "xls":
                return R.mipmap.ic_xlxs;
        }
        return R.mipmap.ic_file;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
        notifyChange();
    }

    public void setFileList(List<File> fileList) {
        data.setFilesAndFolders(fileList);
        this.fileList = fileList;
        notifyChange();
    }

    public void openFolder(Context context, File selectedFile) {
        try {
            FilesAndFoldersListViewModel filesAndFoldersListViewModel = new FilesAndFoldersListViewModel(selectedFile.getAbsolutePath());
            adapter.setFileList(filesAndFoldersListViewModel.getFileList());
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(context, FolderActivity.class);
            intent.putExtra("path", selectedFile.getAbsolutePath());
            intent.putExtra("folderName", selectedFile.getName());
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Can't open folder", Toast.LENGTH_SHORT).show();
        }
    }

    public void openFile(Context context, File selectedFile) {
        try {
            Uri uri = FileProvider.getUriForFile(context, "com.example.srvfilemanager.fileprovider", selectedFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent chooser = Intent.createChooser(intent, "Open file with");
            context.startActivity(chooser);
        } catch (Exception e) {
            Log.e("error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(context, "Can't open file", Toast.LENGTH_SHORT).show();
        }
    }



    public void showFilePopupMenu(File selectedFile, View v) {
//        PopupMenu popupMenu= new PopupMenu(v.getContext(),v);
//        popupMenu.getMenu().add("Rename");
//        popupMenu.getMenu().add("Copy");
//        popupMenu.getMenu().add("Cut");
//        popupMenu.getMenu().add("Delete");
//        popupMenu.setForceShowIcon(true);
//        popupMenu.show();
//        popupMenu.setOnMenuItemClickListener(menuItem -> {
//            if(menuItem.getTitle().equals("Delete")){
//                Uri uri = FileProvider.getUriForFile(v.getContext(), "com.example.srvfilemanager.fileprovider", selectedFile);
//                try {
//                    v.getContext().getContentResolver().delete(uri, selectedFile.getAbsolutePath(), null);
//                    Log.i("Delete","Deleted");
//                    v.setVisibility(View.GONE);
//                }
//                catch (Exception e){
//                    Log.e("Delete", Objects.requireNonNull(e.getMessage()));
//                }
//            }
//            if(menuItem.getTitle().equals("Rename")){
//                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
//                dialog.setTitle("Enter new name for " +selectedFile.getName());
//                dialog.setCancelable(true);
//                final EditText editText=new EditText(v.getContext());
//                dialog.setView(editText);
//
//                dialog.setPositiveButton("Rename", (dialogInterface, i) -> {
//                    String mimeType = FilenameUtils.getExtension(selectedFile.getName());
//                    File newNameFile = new File(selectedFile.getParent()+"/"+editText.getText().toString()+"."+mimeType);
//                    selectedFile.renameTo(newNameFile);
//                    Log.d("Rename",selectedFile.getName());
//                    Log.d("Rename to",selectedFile.getAbsolutePath());
//                    notifyChange();
//                    v.setVisibility(View.VISIBLE);
//                });
//                dialog.show();
//
//
//            }
//            return false;
//        });
    }

    public void onClickAddFolder() {

        final AlertDialog.Builder newAlertDialog = new AlertDialog.Builder(contex.getApplicationContext());
        newAlertDialog.setTitle("New Folder");
        final EditText editText = new EditText(contex.getApplicationContext());
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        newAlertDialog.setView(editText);
        Log.i("Test", "Onclick new Folder");
        newAlertDialog.setPositiveButton("OK", (dialogInterface, i) -> {
        });
        newAlertDialog.show();
    }

    public void reName() {
        Log.i("Rename", "1");
    }
}
