package com.example.srvfilemanager.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.srvfilemanager.models.StorageHelper;

public class HomePageViewModel extends BaseObservable {

    private String mUsedStorage;
    private String mAvailableStorage;
    private String mPercentString;
    private int mPercent;

    public HomePageViewModel(String path) {
        super();
        StorageHelper storageHelper = new StorageHelper(path);
        this.mAvailableStorage = storageHelper.getAvailableStorage();
        this.mUsedStorage= storageHelper.getUsedStorage();
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
}
