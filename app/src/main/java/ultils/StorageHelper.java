package ultils;
import android.os.StatFs;

import com.example.srvfilemanager.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StorageHelper {
    private long bytesAvailable;
    private long bytesTotal;
    private double megAvailable;
    private double megTotal;
    private NumberFormat formatter;
    private String mUsedStorage;
    private  String mTotalStorage;
    private  String mAvailableStorage;
    private StatFs stat;
    private String TAG = "StorageHelper";
    private int mFileCount;
    private int percent=0;
    public StorageHelper(String path) {
        super();
        this.stat = new StatFs(path);
        this.bytesAvailable = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
        this.bytesTotal =(long)stat.getTotalBytes();
        this.megAvailable = (double)bytesAvailable / (1000 * 1000*1000);
        this.megTotal = (double)bytesTotal/(1000*1000*1000);
        this.formatter = new DecimalFormat("#0.00");
        this.mAvailableStorage = ""+ formatter.format(megAvailable) +" GB";
        this.mUsedStorage= ""+ formatter.format(megTotal-megAvailable) +" GB";
        this.percent = (int)(100*(megTotal-megAvailable)/megTotal);
    }

    public long getBytesAvailable() {
        return bytesAvailable;
    }

    public long getBytesTotal() {
        return bytesTotal;
    }

    public double getMegAvailable() {
        return megAvailable;
    }

    public double getMegTotal() {
        return megTotal;
    }

    public NumberFormat getFormatter() {
        return formatter;
    }

    public String getUsedStorage() {
        return mUsedStorage;
    }

    public String getTotalStorage() {
        return mTotalStorage;
    }

    public String getAvailableStorage() {
        return mAvailableStorage;
    }

    public StatFs getStat() {
        return stat;
    }

    public String getTAG() {
        return TAG;
    }

    public int getFileCount() {
        return mFileCount;
    }

    public int getPercent() {

        return percent;
    }
}
