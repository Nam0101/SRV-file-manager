package com.example.srvfilemanager.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import android.widget.SearchView;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.PropertyChangeRegistry;

import com.example.srvfilemanager.BR;
import com.example.srvfilemanager.models.StorageHelper;
import com.example.srvfilemanager.ui.FolderActivity;

public class HomePageViewModel extends BaseObservable {
    private static final int DOCUMENTS = 0;
    private static final int IMAGES = 1;
    private static final int VIDEOS = 2;
    private static final int MUSIC = 3;
    Context context;
    private String mUsedStorage;
    private String mAvailableStorage;
    private String mPercentString;
    private int mPercent;
    private final PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    private String searchQuery = "";

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

    @Bindable
    public String getSearchQuery() {
        return searchQuery;
    }

    @Bindable
    public void setSearchQuery(String query) {
        if (!searchQuery.equals(query)) {
            searchQuery = query;
            notifyPropertyChanged(BR.searchQuery);

        }
    }
    public void onClickFolder() {
        Intent intent = new Intent(context, FolderActivity.class);
        context.startActivity(intent);
    }

    public void onClickHome() {
        Intent intent = new Intent(context, FolderActivity.class);
        context.startActivity(intent);
    }

    public void onClickDocumentFileFilter() {
        Intent intent = new Intent(context, FolderActivity.class);
        intent.putExtra("filter", DOCUMENTS);
        intent.putExtra("folderName", "Documents");
        context.startActivity(intent);
    }

    public void onClickImageFileFilter() {
        Intent intent = new Intent(context, FolderActivity.class);
        intent.putExtra("filter", IMAGES);
        intent.putExtra("folderName", "Images");
        context.startActivity(intent);
    }

    public void onClickVideoFileFilter() {
        Intent intent = new Intent(context, FolderActivity.class);
        intent.putExtra("filter", VIDEOS);
        intent.putExtra("folderName", "Videos");
        context.startActivity(intent);
    }

    public void onClickMusicFileFilter() {
        Intent intent = new Intent(context, FolderActivity.class);
        intent.putExtra("filter", MUSIC);
        intent.putExtra("folderName", "Music");
        context.startActivity(intent);
    }

    public void refreshList() {
        notifyChange();
    }
}