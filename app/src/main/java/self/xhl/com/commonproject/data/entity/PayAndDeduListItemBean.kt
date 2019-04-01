package com.tongji.braindata.data.entity

import java.math.BigDecimal

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 *
 * Created by Administrator on 2018\9\8 0008.
 * 协议，贷款支付和扣款列表Item
参数名	类型	说明
id	        Long	主键
number	   String	编号
contractId	Long	签约id
contractNumber	String	签约编号
contractType	Integer	签约类型
customer	String	客户
mobile	String	手机号
status	Integer	扣款金额
remark	String	备注(失败原因)
createDate	Date	创建时间
*/

class PayAndDeduListItemBean (var id:String?,
                              var number:String?,
                              var contractId:String?,
                              var contractNumber:String?,
                              var contractType:String?,
                              var customer:String?,
                              var mobile:String?,
                              var amount:BigDecimal?,
                              var status:String?,
                              var remark:String?,
                              var createDate:String?)
