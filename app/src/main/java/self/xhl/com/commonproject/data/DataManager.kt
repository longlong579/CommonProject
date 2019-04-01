package com.tongji.braindata.data



import okhttp3.RequestBody
import self.xhl.com.net.app.BaseApp

import self.xhl.com.net.netutil.SignHelper
import java.math.BigDecimal

/**
 * 数据访问的Manager（来源：网络，DB, SP， File）
 * @author bingo
 * @version 1.0.0
 */
object DataManager {

    private val apiService by lazy {
        BaseApp.instance.genNetworkClient(false).create(ApiService::class.java)
    }

    /**
    * 注册
    */
    fun register(requestBody:RequestBody )=apiService.register(requestBody)

    /**
     * 登录
     */
    fun login(mobile: String,password: String)=apiService.login(mobile, SignHelper.genPostSign(password))

    /**
    * 获取注册验证码
     */
    fun getCode(mobile: String)=apiService.getCode(mobile)

    /**
     * 获取修改密码验证码
     */
    fun getCPCode(mobile: String) = apiService.getCPCode(mobile)

    /**
    * 修改密码
     */
    fun changPassword(mobile: String,code:String,newPass:String)=apiService.changPassword(mobile,code,SignHelper.genPostSign(newPass))

    /**
     * 注销登录
     */
    fun logout() = apiService.logout()

    /**
    * 获取银行列表
    */
    fun getBankList()= apiService.getBankList()

    /**
    * 协议签约
    */
    fun agreementSign(requestBody: RequestBody)= apiService.agreementSign(requestBody)

    /**
    * 贷款签约
    */
    fun loanSign(requestBody: RequestBody)= apiService.loanSign(requestBody)


    /**
     * 协议签约验证码
     */
    fun getSigncode(requestBody: RequestBody)= apiService.getSigncode(requestBody)

    /**
     * 贷款签约验证码
     */
    fun getLoanSigncode(requestBody: RequestBody)= apiService.getLoanSigncode(requestBody)


    /**
     * 签约扣款列表 分页
     */
    fun getGrmDedPList(current:Int,size:Int,idCard: String)= apiService.getGrmDedPList(current,size,idCard)

    /**
     * 贷款扣款列表 分页
     */
    fun getLoanDedPList(current:Int,size:Int,idCard: String)= apiService.getLoanDedPList(current,size,idCard)
    /**
     * 扣款
     */
    fun deducation(id: String?,amount: BigDecimal?)= apiService.deducation(id,amount)


    /**
     * 协议支付 贷款支付 贷款扣款列表
     */
    fun getAgrPayDedPageList(current: Int, size: Int, status: Int) = apiService.getAgrPayDedPageList(current, size, status)

 /**
     * 贷款支付 贷款支付 贷款扣款列表
     */
    fun getLoanPayDedPageList(current: Int, size: Int, status: Int) = apiService.getLoanPayDedPageList(current, size, status)

    /**
     * qiniutoken
     */
     fun getQiniuToken()= apiService.getQiniuToken()

    /**
     * 联系电话
     */
     fun getConnectPhone()= apiService.getConnectPhone()

    /**
     * 关于我们
     */
    fun getAboutUs()= apiService.getAboutUs()

    /**
     * 服务
     */
    fun getServer()= apiService.getServer()

    /**
     * 更新
     */
    fun checkUpdadte(terminal: Int, versionNum: String)= apiService.checkUpdadte(terminal,versionNum)

}