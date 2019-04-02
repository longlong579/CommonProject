package com.tongji.braindata.data.RequestBean

/**
 * Created by Administrator on 2018\9\8 0008.
 * 注册请求体
 */
data class RegisterReqBean (
    var companyId: String="",//企业识别号
    var name: String,//用户名
    var mobile: String,//电话
    var mail: String,//邮箱
    var password: String,//密码
    var code: String//验证码
)