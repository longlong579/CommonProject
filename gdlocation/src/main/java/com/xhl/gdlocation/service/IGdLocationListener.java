package com.xhl.gdlocation.service;

/**
 * @author xhl
 * @desc 2019/3/12 18:28
 */
public interface IGdLocationListener {
    void gdLocationReceive(GdLocationInfo gdLocationInfo);
    /**
     * 失败
     *
     * @param errCode 错误码
     * @param errInfo 错误信息
     */
    void onFail(int errCode, String errInfo);
}
