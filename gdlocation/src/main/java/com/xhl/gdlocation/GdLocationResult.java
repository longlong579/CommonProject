package com.xhl.gdlocation;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.location.AMapLocation;

/**
 * @author xhl
 * 封装自己需要的内容  不提供POI内容 此接口只提供基本的定位信息 供外部使用
 * 权限管理交由外部处理
 * @desc 2019/3/13 10:19
 */
public class GdLocationResult implements Parcelable {
    private int locationType;//定位方式
    private String locationDetail;
    private double latitude;
    private double longitude;
    private float accuracy;
    private double altitude;//高度
    private float speed;//速度
    private float bearing;//方位
    private String buildingId;//建筑id
    private String floor;
    private String address;//具体位置信息
    private String country;
    private String province;//高德字符串默认是""   高德地图不返回null 而是返回"" 省的到时候StringBuilder时将null-->"null"(百度返回的是null)
    private String city;//城市 默认是""
    private String district;
    private String street;
    private String streetNum;
    private String cityCode;//高德自己的城市编码 不是国家地理编码
    private String adCode;//地区地理位置编码 全国统一的
    private String poiName;
    private String aoiName;
    private int gpsStatus;


    GdLocationResult(AMapLocation location) {
        this.locationType = location.getLocationType();
        this.locationDetail = location.getLocationDetail();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.accuracy = location.getAccuracy();
        this.altitude = location.getAltitude();
        this.speed = location.getSpeed();
        this.bearing = location.getBearing();
        this.buildingId = location.getBuildingId();
        this.floor = location.getFloor();
        this.address = location.getAddress();
        this.country = location.getCountry();
        this.province = location.getProvince();
        this.city = location.getCity();
        this.district = location.getDistrict();
        this.street = location.getStreet();
        this.streetNum = location.getStreetNum();
        this.cityCode = location.getCityCode();
        this.adCode = location.getAdCode();
        this.poiName = location.getPoiName();
        this.aoiName = location.getAoiName();
        this.gpsStatus = location.getGpsAccuracyStatus();

    }


    public int getLocationType() {
        return locationType;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public float getSpeed() {
        return speed;
    }

    public float getBearing() {
        return bearing;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getFloor() {
        return floor;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getAdCode() {
        return adCode;
    }

    public String getPoiName() {
        return poiName;
    }

    public String getAoiName() {
        return aoiName;
    }

    public int getGpsStatus() {
        return gpsStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.locationType);
        dest.writeString(this.locationDetail);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeFloat(this.accuracy);
        dest.writeDouble(this.altitude);
        dest.writeFloat(this.speed);
        dest.writeFloat(this.bearing);
        dest.writeString(this.buildingId);
        dest.writeString(this.floor);
        dest.writeString(this.address);
        dest.writeString(this.country);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.street);
        dest.writeString(this.streetNum);
        dest.writeString(this.cityCode);
        dest.writeString(this.adCode);
        dest.writeString(this.poiName);
        dest.writeString(this.aoiName);
        dest.writeInt(this.gpsStatus);
    }

    private GdLocationResult(Parcel in) {
        this.locationType = in.readInt();
        this.locationDetail = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.accuracy = in.readFloat();
        this.altitude = in.readDouble();
        this.speed = in.readFloat();
        this.bearing = in.readFloat();
        this.buildingId = in.readString();
        this.floor = in.readString();
        this.address = in.readString();
        this.country = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.street = in.readString();
        this.streetNum = in.readString();
        this.cityCode = in.readString();
        this.adCode = in.readString();
        this.poiName = in.readString();
        this.aoiName = in.readString();
        this.gpsStatus = in.readInt();
    }

    public static final Creator<GdLocationResult> CREATOR = new Creator<GdLocationResult>() {
        @Override
        public GdLocationResult createFromParcel(Parcel source) {
            return new GdLocationResult(source);
        }

        @Override
        public GdLocationResult[] newArray(int size) {
            return new GdLocationResult[size];
        }
    };
}