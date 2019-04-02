package com.tongji.braindata.data.entity



/**
* @作者 xhl
*
* @创建时间 2018/9/6 13:05
*/
class BaseBean<T> {

    /**
     * success : true
     * message : 请求成功
     * data : {}
     * code : 01
     */
    var success: Boolean = false
    var message: String? = null
    var data: T? = null
    var code: Int = 0

    override fun toString(): String {
        return "BaseBean{" +
                "success=" + success +
                ", message='" + message + '\''.toString() +
                ", data=" + data +
                ", code=" + code +
                '}'.toString()
    }
}
