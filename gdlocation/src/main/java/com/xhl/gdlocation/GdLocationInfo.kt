package com.xhl.gdlocation

/**
 * @author xhl
 * 封装自己需要的内容
 * @desc 2019/3/13 10:19
 */
class GdLocationInfo {
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var province:String=""
    var pcd: String = ""//省市区  高德地图不返回null 而是返回"" 省的到时候StringBuilder时将null-->"null"(百度返回的是null)
}
