package com.example.srvfilemanager.diffutil;

import androidx.recyclerview.widget.DiffUtil;

import java.io.File;
import java.util.List;

public class FileAndFolderDiffUtil extends DiffUtil.Callback {
    private List<File> oldList;
    private List<File> newList;

    public FileAndFolderDiffUtil(List<File> oldList, List<File> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getPath().equals(newList.get(newItemPosition).getPath());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        File oldFile = oldList.get(oldItemPosition);
        File newFile = newList.get(newItemPosition);
        return oldFile.getName().equals(newFile.getName()) &&
                oldFile.getPath().equals(newFile.getPath()) &&
                oldFile.length() == newFile.length();
    }
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
