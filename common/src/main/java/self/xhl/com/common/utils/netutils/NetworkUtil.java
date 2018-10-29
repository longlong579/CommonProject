package self.xhl.com.common.utils.netutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author bingo
 * @version 1.0.0
 */

public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context) {
        boolean res = false;
        int reTry = 3;

        // if(NetworkUtil.getIMSI(context) == null){
        // return res;
        // }

//		if (isAirplaneModeOn(context)) {
//			return res;
//		}

        for (int i = 0; i < reTry; i++) {
            try {
                ConnectivityManager nm = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = nm.getActiveNetworkInfo();
                if (null != networkInfo) {
                    res = networkInfo.isConnected();
                }
                break;
            } catch (Exception e) {

            }
        }
        return res;
    }

    public static String getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
            return "CDMA";
        } else if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            return "GSM";
        } else {
            return "WCDMA";
        }
    }

    public static boolean isServiceAvailable(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
            return true;
        }
        return false;
    }

    public static String getMobilePhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getSimOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimOperatorName();
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return ni.getTypeName();
        }
        return null;
    }

    public static String getSSN(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    public static String filterNumber(String srcNumber) {
        String destNumber = srcNumber;
        if (destNumber.contains("+86")) {
            destNumber = destNumber.replace("+86", "");
        }
        if (destNumber.contains("17951")) {
            destNumber = destNumber.replace("17951", "");
        }
        return destNumber;
    }

    /**
     * 判断是否为飞行模式
     *
     * @param context
     * @return¡
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    public static boolean hasNoIMSI(Context context) {
        return getIMSI(context) == null;
    }

    public static String getUserAgent(Context context) {
        WebView wv = new WebView(context);
        WebSettings ws = wv.getSettings();
        return ws.getUserAgentString();
    }

    public static String getMacAddress(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }
}