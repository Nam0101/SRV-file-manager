package com.example.srvfilemanager.ui.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.srvfilemanager.R;

import java.io.File;
public class FileAndFoldersAdapter extends RecyclerView.Adapter<FileAndFoldersAdapter.FileViewHolder> {
    private File[] filesAndFolders;

    public FileAndFoldersAdapter(File[] filesAndFolders) {
        this.filesAndFolders = filesAndFolders;
    }
    @NonNull
    @Override
    public FileAndFoldersAdapter.FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_single_item,parent,false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAndFoldersAdapter.FileViewHolder holder, int position) {
        File selectedFile = filesAndFolders[position];
        if (selectedFile.isDirectory()) {
            holder.mImageView.setImageResource(R.mipmap.ic_folder);
            holder.mTextViewItemCount.setText(selectedFile.listFiles().length + " items");
        } else {
            holder.mImageView.setImageResource(R.mipmap.ic_button_doc);
            holder.mTextViewItemCount.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextViewName,mTextViewItemCount,mTextViewSize;

        public FileViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_folder_or_file);
            mTextViewName = itemView.findViewById(R.id.text_view_name);
            mTextViewItemCount = itemView.findViewById(R.id.text_view_count);
            mTextViewSize = itemView.findViewById(R.id.text_view_size);
        }
    }
}
