package self.xhl.com.common.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import self.xhl.com.common.utils.netutils.NetworkUtil;

/**
 * @author bingo
 * @version 1.0.0
 */

public class DeviceManager {
    public static DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo();
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        deviceInfo.imei = tm.getDeviceId();
        deviceInfo.imsi = tm.getSubscriberId();
        deviceInfo.phoneType = NetworkUtil.getPhoneType(context);
        deviceInfo.phoneNumber = tm.getLine1Number();
        deviceInfo.android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceInfo.simOperator = tm.getSimOperator();
        deviceInfo.simOperatorName = tm.getSimOperatorName();
        deviceInfo.simContryIso = tm.getSimCountryIso();
        deviceInfo.networkType = NetworkUtil.getNetworkType(context);
        deviceInfo.ssn = tm.getSimSerialNumber();
        deviceInfo.userAgent = System.getProperty("http.agent");
        deviceInfo.wifiMac = NetworkUtil.getMacAddress(context);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        deviceInfo.btMac = bluetoothAdapter != null ? bluetoothAdapter.getAddress() : null;
        deviceInfo.board = Build.BOARD;
        deviceInfo.bootloader = Build.BOOTLOADER;
        deviceInfo.brand = Build.BRAND;
        deviceInfo.cpuAbi = Build.CPU_ABI;
        deviceInfo.deviceParms = Build.DEVICE;
        deviceInfo.displayInfo = Build.DISPLAY;
        deviceInfo.fingerprint = Build.FINGERPRINT;
        deviceInfo.hardware = Build.HARDWARE;
        deviceInfo.host = Build.HOST;
        deviceInfo.versionId = Build.ID;
        deviceInfo.manufacturer = Build.MANUFACTURER;
        deviceInfo.model = Build.MODEL;
        deviceInfo.product = Build.PRODUCT;
        deviceInfo.serial = Build.SERIAL;
        deviceInfo.tags = Build.TAGS;
        deviceInfo.builderType = Build.TYPE;
        deviceInfo.userInfo = Build.USER;
        deviceInfo.osVersion = Build.VERSION.RELEASE;
        deviceInfo.incremental = Build.VERSION.INCREMENTAL;
        return deviceInfo;
    }

    private static class DeviceInfo {
        public String deviceToken; //设备token
        public String imsi; // 国际移动用户识别码 IMSI(SIM), eg:310260000000000
        public String imei; // 移动设备国际识别码 IMEI eg:359881030314356
        public String wifiMac; //Wifi Mac Address, eg:48:6b:2c:60:bb:44
        public String btMac; //蓝牙的MAC地址, eg:48:6B:2C:60:BB:43
        public String phoneNumber; //手机号, eg:15555215554
        public String android_id; //在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置。
        public String cpuAbi; //cpu指令集,eg:armeabi-v7a
        public String phoneType; //电话类型, eg:CDMA/GSM/WCDMA
        public String ssn; //SIM卡的序列号,eg:89014103211118510720
        public String userAgent; //eg:Dalvik/1.6.0 (Linux; U; Android 4.3; vivo X3L Build/JLS36C)
        public String networkType; // 网络类型, eg: WIFI
        public String board; //主板, eg: MSM8226
        public String bootloader; //eg: unknown
        public String brand; //android系统定制商 eg:vivo
        public String incremental; // 源码控制版本号 eg:eng.buildbot.20150609.195841
        public String deviceParms; //设备参数, eg:msm8226
        public String displayInfo; //显示屏参数, eg: JLS36C
        public String fingerprint; //ROM版本, eg: vivo/msm8226/msm8226:4.3/JLS36C/eng.compiler.20141030.171608:user/dev-keys
        public String hardware; //硬件名称, eg: qcom
        public String host; // eg:compiler014
        public String versionId; //修订版本列表 eg: JLS36C
        public String manufacturer; //硬件制造商 eg:BBK
        public String model; //手机型号 eg: vivo X3L
        public String product; //手机制造商 eg:msm8226
        public String serial; //序列号 eg:83106339
        public String tags; //描述build的标签 eg:dev-keys
        public String builderType; //builder类型, eg:user
        public String userInfo; //eg:compiler
        public String osVersion; //Firmware/OS 版本号, eg:4.3
        public String simOperatorName;
        public String simOperator; //MCC+MNC代码(SIM卡运营商国家代码和运营商网络代码)(IMSI),eg:310260
        public String simContryIso; // SIM卡提供商的国家代码, eg:us
    }
}
