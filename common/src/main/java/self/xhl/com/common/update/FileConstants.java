package self.xhl.com.common.update;

import android.os.Environment;

import java.io.File;

import self.xhl.com.common.app.BaseApp;

/**
 * @author bingo
 * @date 2017/3/13
 */

public final class FileConstants {
    public static final File ROOT = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), BaseApp.instance.getPackageName());
    public static final File IMG_USER_PIC_CACHE;
    public static final File TEMP_CACHE;
    public static final File FILE_APK;
    public static final File LOG;

    public FileConstants() {
    }

    static {
        IMG_USER_PIC_CACHE = new File(ROOT, "pic");
        TEMP_CACHE = new File(ROOT, "temp");
        FILE_APK = new File(ROOT, "apk");
        LOG = new File(ROOT, "log");
    }
}
