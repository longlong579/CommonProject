package com.xhl.gdlocation;

/**
 * @author xhl
 * @desc 2019/3/12 18:28
 */
public interface IGdLocationListener {
    void gdLocationReceive(GdLocationResult gdLocationInfo);
    /**
     * 失败
     *
     * @param errCode 错误码 12:定位权限
     * @param errInfo 错误信息
     */
    void onFail(int errCode, String errInfo);
}
