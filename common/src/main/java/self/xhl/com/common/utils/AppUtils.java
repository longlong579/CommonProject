package self.xhl.com.common.utils;

import android.content.Context;

import java.lang.reflect.Field;

public class AppUtils {
    private static Boolean isDebug = null;

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    /**
     * Sync lib debug with app's debug value. Should be called in module Application
     *
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            try {
                String packageName = context.getPackageName();
                Class buildConfig = Class.forName(packageName + ".BuildConfig");
                Field DEBUG = buildConfig.getField("DEBUG");
                DEBUG.setAccessible(true);
                isDebug = DEBUG.getBoolean(null);
            } catch (Throwable t) {
                // Do nothing
            }
        }
    }
}
