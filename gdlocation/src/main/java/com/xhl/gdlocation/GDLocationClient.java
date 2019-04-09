package com.xhl.gdlocation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import static com.amap.api.location.AMapLocation.ERROR_CODE_FAILURE_LOCATION_PERMISSION;


/**
 * @作者 xhl
 * 高德定位 注意：在主项目AndroidMainifest中添加： 服务 和 key:（去申请）
 * <service android:name="com.amap.api.location.APSService" />
 * <meta-data
 * android:name="com.amap.api.v2.apikey"
 * android:value="4e66291e968acaa610d413af80d5a36f"/>
 * 2:Context 传getApplication()
 * @创建时间 2019/3/13 9:57
 */
/*
  应用：
  fun locate()
  {
        MapLocationClient.newBuilder(this)
                .cacheEnable(true)
                .build()
                .locate {

                }

    }
 */

public class GDLocationClient {
    AMapLocationClient mlocationClient;
    boolean isOnceLocation;

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    private GDLocationClient(Context context, Builder builder) {

        mlocationClient = new AMapLocationClient(context);
        AMapLocationClientOption option = new AMapLocationClientOption();

        isOnceLocation=builder.onceLocation;
        option.setLocationMode(builder.mode == GdLocationMode.Height_Accuracy ?
                AMapLocationClientOption.AMapLocationMode.Battery_Saving :
                builder.mode == GdLocationMode.Battery_Saving ?
                        AMapLocationClientOption.AMapLocationMode.Battery_Saving :
                        AMapLocationClientOption.AMapLocationMode.Device_Sensors)
                .setOnceLocation(builder.onceLocation)
                .setInterval(2000)
                .setNeedAddress(builder.needAddress);

        option.setMockEnable(builder.mockEnable);
        option.setWifiScan(builder.wifiScan);
        option.setHttpTimeOut(builder.timeout);
        option.setLocationCacheEnable(builder.cache);
        option.setOnceLocationLatest(builder.onceLocationLatest);


        mlocationClient.setLocationOption(option);
    }

    public void locate(final IGdLocationListener mListener) {

        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(com.amap.api.location.AMapLocation aMapLocation) {
                if (mListener != null && aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
                        //定位成功回调信息，设置相关消息
                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表

                        //注意：高德地图若获取不到省市区 字符串返回的是"" 百度则返回的是null StringBuilder().append()时 null->"null"
                        GdLocationResult gdLocationInfo = new GdLocationResult(aMapLocation);
                        mListener.gdLocationReceive(gdLocationInfo);

                    } else {
                        mListener.onFail(aMapLocation.getErrorCode(), aMapLocation.getErrorInfo());
                    }

                }

                if (isOnceLocation) {
                    mlocationClient.stopLocation();
                    mlocationClient.onDestroy();
                }
            }
        });

        mlocationClient.startLocation();

    }


    public static class Builder {

        @GdLocationMode.LocationMode
        private int mode;//扫描模式 默认高精度
        private boolean onceLocation=true;//默认 定位一次
        private boolean onceLocationLatest; //默认获取连续几次中定位精度最高的那次
        //        private long interval;
        private boolean needAddress; //默认 需要详细地址
        private boolean wifiScan;//默认 允许wife刷新
        private boolean mockEnable;
        private long timeout;
        private boolean cache;//默认 不需要缓存
        private Context context;

        //设置默认参数
        private Builder(Context context) {
            this.context = context.getApplicationContext();
            mode =GdLocationMode.Height_Accuracy;
            needAddress = true;
            wifiScan = true;
            onceLocationLatest = true;
            timeout = 20000;
            mockEnable = false;//模拟定位
            cache = false;
        }


        public Builder locationMode(@GdLocationMode.LocationMode  int mode) {
            this.mode = mode;
            return this;
        }

        public Builder onceLocationlatest(boolean onceLocationLatest) {
            this.onceLocationLatest = onceLocationLatest;
            return this;
        }

        public Builder onceLocation(boolean onceLocation) {
            this.onceLocation = onceLocation;
            return this;
        }
/*
        public Builder onceLocationLatest(boolean onceLocationLatest) {
            this.onceLocationLatest = onceLocationLatest;
            return this;
        }

        public Builder interval(long interval) {
            this.interval = interval;
            return this;
        }*/

        public Builder needAddress(boolean needAddress) {
            this.needAddress = needAddress;
            return this;
        }

        public Builder wifiScan(boolean wifiScan) {
            this.wifiScan = wifiScan;
            return this;
        }

        public Builder mockEnable(boolean mockEnable) {
            this.mockEnable = mockEnable;
            return this;
        }

        public Builder httpTimeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder cacheEnable(boolean cache) {
            this.cache = cache;
            return this;
        }


        public GDLocationClient build() {

            return new GDLocationClient(context, this);
        }

    }

    //当利用高德做长时间定位的时候 记得在MainActivity的onDestroy中调用
    public void onDestroy()
    {
        if(!isOnceLocation && mlocationClient!=null)
        {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
    }
}
