package com.xhl.gdlocation.service;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


/**
 * @作者 xhl
 * 高德定位 注意：在AndroidMainifest中添加： 服务 和 key:（去申请）
 * <service android:name="com.amap.api.location.APSService" />
 * <meta-data
 * android:name="com.amap.api.v2.apikey"
 * android:value="4cd142245976e4196dda0afc355e55a6"/>
 * 2:Context 传getApplication()
 * @创建时间 2019/3/13 9:57
 */

public class GDLocationServer {
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private GDLocationServer() {
        initOption();
    }

    public static GDLocationServer getInstance() {
        return Inner.gdLocationServer;
    }

    private static class Inner {
        private static GDLocationServer gdLocationServer = new GDLocationServer();
    }

    private void initOption(){
        //初始化定位参数 默认设置 只定位一次 有详细地址
         mLocationOption = new AMapLocationClientOption()
                .setNeedAddress(true)//设置是否返回地址信息（默认返回地址信息）
                .setLocationMode(AMapLocationClientOption.
                        AMapLocationMode.Hight_Accuracy)//设置定位模式为高精度模式
                //   .setInterval(Constants.upload_position_time)//设置定位间隔,单位毫秒,默认为2000ms
                .setOnceLocation(true);//获取一次定位结果
        mLocationOption.setOnceLocationLatest(true);//获取最近3s内精度最高的一次定位结果
    }

    public void getLocation(Context context, final IGdLocationListener mListener) {

        mlocationClient= new AMapLocationClient(context);
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (mListener != null && aMapLocation!=null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表

                        //注意：高德地图若获取不到省市区 字符串返回的是"" 百度则返回的是null StringBuilder().append()时 null->"null"
                        GdLocationInfo gdLocationInfo = new GdLocationInfo();
                        gdLocationInfo.setLatitude(aMapLocation.getLatitude());
                        gdLocationInfo.setLongitude(aMapLocation.getLongitude());
                        gdLocationInfo.setProvince(aMapLocation.getProvince());
                        gdLocationInfo.setCity(aMapLocation.getCity());
                        gdLocationInfo.setDistrict(aMapLocation.getDistrict());
                        gdLocationInfo.setDisCode(aMapLocation.getAdCode());
                        //如果不是在循环中 直接用String+替代StringBuilder Android已帮做了优化
                        gdLocationInfo.setPcd(aMapLocation.getProvince()+aMapLocation.getCity()+aMapLocation.getDistrict());
                        mListener.gdLocationReceive(gdLocationInfo);

                    } else {
                        mListener.onFail(aMapLocation.getErrorCode(), aMapLocation.getErrorInfo());
                    }

                }
                mlocationClient.stopLocation();
                mlocationClient.onDestroy();
            }
        });
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除


        //启动定位
        mlocationClient.startLocation();
    }
}
