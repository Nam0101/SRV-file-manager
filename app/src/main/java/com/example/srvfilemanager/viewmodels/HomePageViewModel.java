package com.example.srvfilemanager.viewmodels;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.srvfilemanager.models.StorageHelper;
import com.example.srvfilemanager.ui.FolderActivity;

public class HomePageViewModel extends BaseObservable {
    Context context;
    private String mUsedStorage;
    private String mAvailableStorage;
    private String mPercentString;
    private int mPercent;

    public HomePageViewModel(String path, Context context) {
        super();
        this.context = context;
        StorageHelper storageHelper = new StorageHelper(path);
        this.mAvailableStorage = storageHelper.getAvailableStorage();
        this.mUsedStorage = storageHelper.getUsedStorage();
        this.mPercentString = storageHelper.getPercentString();
        this.mPercent = storageHelper.getPercent();
    }

    @Bindable
    public String getUsedStorage() {
        return mUsedStorage;
    }

    @Bindable
    public String getAvailableStorage() {
        return mAvailableStorage;
    }

    @Bindable
    public String getPercentString() {
        return mPercentString;
    }

    @Bindable
    public int getPercent() {
        return mPercent;
    }

    public void onClickFolder() {
        Intent intent = new Intent(context, FolderActivity.class);
        context.startActivity(intent);
    }
    public void onClickHome() {
        Intent intent = new Intent(context, FolderActivity.class);
        context.startActivity(intent);
    }
    public void onClickDocumentFileFilter(){
        Intent intent = new Intent(context, FolderActivity.class);
        intent.putExtra("filter",".png,.jpg,.srt");
        context.startActivity(intent);

    }
}
