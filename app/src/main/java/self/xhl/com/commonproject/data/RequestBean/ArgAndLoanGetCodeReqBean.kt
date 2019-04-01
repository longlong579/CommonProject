package com.tongji.braindata.data.RequestBean


/**
 * 协议 和 贷款 签约请求体
 *
 *   参数名	必选	类型	说明
customer	是	String	客户
idCard	是	String	身份证号
bank	是	String	银行名称
bankCard	是	String	银行卡号
mobile	是	String	预留手机号
amount	是	BigDecimal	扣款金额
idCardFront	是	String	身份证正面
idCardBack	是	String	身份证反面
bankFront	是	String	银行卡正面
bankBack	是	String	银行卡反面
agreementImg	是	String	协议图片多张
periodization	是	Integer	分期次数
 **/
data class ArgAndLoanGetCodeReqBean(
        var customer: String?,
        var idCard: String?,
        var bank: String?,
        var bankCard: String?,
        var mobile: String?,
        var amount: String?,
        var idCardFront: String?,
        var idCardBack: String?,
        var bankFront: String?,
        var bankBack: String?,
        var agreementImg: String?,
        var periodization: String?
)

