package com.tongji.braindata.data

import android.text.TextUtils
import com.tongji.braindata.data.entity.LoginBean
import com.xhl.base.spdelegate.SPreference


/**
 * @author xhl
 * @desc
 * 2018/9/7 15:17
 */
object UserInfoManager {
    val sPUserInfoName = "userInfoSP"

    var id: String? by SPreference("id","", sPUserInfoName)
    var companyId: String? by SPreference("companyId","",  sPUserInfoName)
    var companyName: String? by SPreference("companyName","",  sPUserInfoName)
    var name: String? by SPreference("name","",  sPUserInfoName)
    var mobile: String? by SPreference("mobile","",  sPUserInfoName)
    var passWord: String? by SPreference("password","",  sPUserInfoName)
    var mail: String? by SPreference("mail", "", sPUserInfoName)
    var token: String? by SPreference("token", "", sPUserInfoName)
    var isChecked: Boolean? by SPreference("hasRead", false, sPUserInfoName)

    fun saveUserInfo(loginInfo: LoginBean,passWord:String) {
        token = loginInfo.token
        id = loginInfo.id
        companyId = loginInfo.companyId
        companyName = loginInfo.companyName
        name = loginInfo.name
        mobile = loginInfo.mobile
        this.passWord=passWord
        mail = loginInfo.mail
        isChecked=true
    }

    fun clearUserInfo() {
        mobile = ""
        passWord=""
        isChecked=false
    }

    fun changePassword()
    {
        passWord=""
    }

    fun isLogin(): Boolean {
        return (!mobile.isNullOrBlank()&& !passWord.isNullOrBlank())
    }
}