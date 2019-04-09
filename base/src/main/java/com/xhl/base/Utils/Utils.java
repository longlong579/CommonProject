package com.xhl.base.Utils;

/**
 * @author xhl
 * @desc 2019/4/9 17:10
 */
public class Utils {

    private static long  lastClickTime=0;
    public static boolean isFastClick(int millisecond) {
        long curClickTime = System.currentTimeMillis();
        long interval = (curClickTime - lastClickTime);

        if (0 < interval && interval < millisecond) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            return true;
        }
        lastClickTime = curClickTime;
        return false;
    }
}
