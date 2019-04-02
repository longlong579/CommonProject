package com.tongji.braindata.data

import com.tongji.braindata.data.entity.*
import okhttp3.RequestBody
import retrofit2.http.*
import io.reactivex.Observable
import self.xhl.com.commonproject.data.URl
import self.xhl.com.commonproject.data.pager.PageRes
import java.math.BigDecimal

/**
 * 网络请求的所有的接口
 *
 * @author bingo
 * @version 1.0.0
 */

interface ApiService {
    /**
     * 注册
     */
//    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(URl.Register)
    fun register(@Body params: RequestBody): Observable<BaseBean<Any>>

    /**
     * 登录
     */
    @GET(URl.Login)
    fun login(@Query("mobile") mobile: String, @Query("password") password: String): Observable<BaseBean<LoginBean>>

    /**
     * 注册获取验证码
     */
    @GET(URl.GetRegisterCode)
    fun getCode(@Query("mobile") mobile: String): Observable<BaseBean<Any>>

    /**
     * 修改密码获取验证码
     */
    @GET(URl.ChangePasswordCode)
    fun getCPCode(@Query("mobile") mobile: String): Observable<BaseBean<Any>>

    /**
     * 修改密码
     */
    @GET(URl.ChangePassword)
    fun changPassword(@Query("mobile") mobile: String, @Query("code") code: String, @Query("newPass") newPass: String): Observable<BaseBean<Any>>

    /**
     * 注销登录
     */
    @GET(URl.Logout)
    fun logout(): Observable<BaseBean<Any>>

    /**
     * 关于我们
     */
    @GET(URl.AboutUs)
    fun getAboutUs(): Observable<BaseBean<String?>>

    /**
     * 服务
     */
    @GET(URl.ServiceInfo)
    fun getServer(): Observable<BaseBean<String?>>


    /**
     * 更新
     */
    @GET(URl.Updata)
    fun checkUpdadte(@Query("terminal") terminal: Int,@Query("versionNum") versionNum: String): Observable<BaseBean<UpdateInfoResp>>


    /**
     * 获取银行列表
     */
    @GET(URl.GetBankList)
    fun getBankList(): Observable<BaseBean<List<BankBean?>?>>




    /**
     * 协议签约
     */
    @POST(URl.AgreementSign)
    fun agreementSign(@Body params: RequestBody): Observable<BaseBean<Any>>

    /**
     * 贷款签约
     */
    @POST(URl.LoanSign)
    fun loanSign(@Body params: RequestBody): Observable<BaseBean<Any>>

    /**
     * 协议签约验证码
     */
    @POST(URl.GetSigncode)
    fun getSigncode(@Body params: RequestBody): Observable<BaseBean<SignCodeBean>>

    /**
     * 贷款签约验证码
     */
    @POST(URl.GetLoanSigncode)
    fun getLoanSigncode(@Body params: RequestBody): Observable<BaseBean<SignCodeBean>>


    /**
     * 扣款
     */
    @GET(URl.Deducation)
    fun deducation(@Query("id") id: String?,@Query("amount") amount: BigDecimal?): Observable<BaseBean<Any>>

    /**
     * 协议扣款列表 分页
     */
    @POST(URl.AgreementDeductionPageList)
    fun getGrmDedPList(@Query("current") current:Int,@Query("size") size:Int,@Query("idCard")idCard: String): Observable<BaseBean<PageRes<List<PayAndDeduListItemBean>>>>

    /**
     * 贷款扣款列表 分页
     */
    @POST(URl.OrderDeductionLoanPageList)
    fun getLoanDedPList(@Query("current") current:Int,@Query("size") size:Int,@Query("idCard")idCard: String): Observable<BaseBean<PageRes<List<PayAndDeduListItemBean>>>>

//    /**
//     * 协议扣款列表 分页
//     */
//    @POST(URl.AgreementDeductionPageList)
//    fun getGrmDedPList(@Query("current") current:Int,@Query("size") size:Int,@Body params: RequestBody): Observable<BaseBean<List<DeductionItemBean>>>
//
//    /**
//     * 贷款扣款列表 分页
//     */
//    @POST(URl.OrderDeductionLoanPageList)
//    fun getLoanDedPList(@Query("current") current:Int,@Query("size") size:Int,@Body params: RequestBody): Observable<BaseBean<List<DeductionItemBean>>>



    /**
     * 协议支付扣款列表 分页
     */
    @POST(URl.GetAgrPayDedPageList)
    fun getAgrPayDedPageList(@Query("current") current: Int, @Query("size") size: Int, @Query("status") status: Int): Observable<BaseBean<PageRes<List<PayAndDeduListItemBean>>>>

/**
     * 贷款支付扣款列表 分页
     */
    @POST(URl.GetLoanPayDedPageList)
    fun getLoanPayDedPageList(@Query("current") current: Int, @Query("size") size: Int, @Query("status") status: Int): Observable<BaseBean<PageRes<List<PayAndDeduListItemBean>>>>


    /**
     *七牛云Token
     */
    @GET(URl.QiNiu)
    fun getQiniuToken():Observable<BaseBean<String?>>

    /**
     *联系电话
     */
    @GET(URl.ConnectPhone)
    fun getConnectPhone(): Observable<BaseBean<String?>>

    /**
     *银行列表
     */
//    @GET(URl.GetBankList)
//    fun getBankList():Observable<BaseBean<BankBean>>

}
