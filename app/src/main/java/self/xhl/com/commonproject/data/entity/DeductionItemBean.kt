package com.tongji.braindata.data.entity

/**
 * Created by Administrator on 2018\9\8 0008.
 * 扣款列表 单条数据
 *
 *  参数名	类型	说明
id	Long	id
number	String	单号
customer	String	客户
mobile	String	预留手机号
amount	BigDecimal	扣款金额
createDate	Date	创建时间返回参数说明
 */
data class DeductionItemBean(val id:String?,
                             val number:String?,
                             val customer:String?,
                             val mobile:String?,
                             val amount:String?,
                             val createDate:String?)