package com.example.srvfilemanager.ui.adapters;

import android.widget.ImageView;
import android.widget.SearchView;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter("app:srcCompat")
    public static void setImageResource(ImageView imageView, int resourceId) {
        imageView.setImageResource(resourceId);
    }
    @BindingAdapter("app:query")
    public static void setQuery(SearchView view, String query) {
        view.setQuery(query, false);
    }


}