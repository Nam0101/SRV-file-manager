package ultils;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllFileInStorage {
    List<File> fileList;

    public AllFileInStorage() {
        fileList = new ArrayList<>();
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void getAllFiles(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        getAllFiles(file);
                    } else {
                        fileList.add(file);
                    }
                }
            }
        }
    }

    public void getAllFilesInStorage() {
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File rootDirectory = new File(rootPath);
        getAllFiles(rootDirectory);
    }
}
