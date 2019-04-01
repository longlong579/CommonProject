package self.xhl.com.commonproject.data;


import self.xhl.com.commonproject.BuildConfig;

/**
 * @author xhl
 * @desc 2018/9/6 13:41
 */
public class URl {
    public static final String Register="user/register";//注册 POST
    public static final String Login="user/login";
    public static final String GetRegisterCode="user/getRegisterCode";
    public static final String ChangePasswordCode="user/getChangePasswordCode";
    public static final String ChangePassword="user/changePassword";
    public static final String Logout="user/logout";
    public static final String ConnectPhone= "config/getContactNumber";//联系电话
    public static final String QiNiu= "config/getQiniuToken";//七牛云
    public static final String GetBankList= "config/getBankList";//获取银行列表 GET 无参数 返回：BankBean


    public static final String GetSigncode="contract/getAgreementCode";//  协议签约验证码 Post
    public static final String GetLoanSigncode="contract/getLoanCode";//  贷款签约验证码 Post
    /**
     * POST
     * 请求体 ：ArgAndLoanSignReqBean
     * 返回:BaseBean<Any>
     */
    public static final String AgreementSign= "contract/agreement";//协议签约
    public static final String LoanSign= "contract/loan";//贷款签约


    /**
     * POST
     * @QUERY 拼接URL idCard:String
     * 请求体 ：DeductionListReqBean   ?疑问
     * 返回:BaseBean<List<DeductionItemBean>>
     */
    public static final String AgreementDeductionPageList="contract/getAgreementPageList";//  协议扣款列表
    public static final String OrderDeductionLoanPageList="contract/getLoanPageList";//  贷款扣款列表
    public static final String Deducation="withhold/withhold";//扣款  GET id	是	Long	签约单ID      amount	是	BigDecimal	要扣款的金额


    /**
     * POST @QUERY 拼接URL
     *  参数名	必选	类型	说明
     * status	是	int	扣款状态 1，成功 2，失败
     *
     */
    public static final String GetAgrPayDedPageList="withhold/getAgreementPageList";//  协议支付和扣款订单列表
    public static final String GetLoanPayDedPageList="withhold/getLoanPageList";//  贷款支付和扣款列表


    //H5
    public static final String AboutUs= BuildConfig.HOST+"config/getAboutUs";//关于我们
    public static final String ServiceInfo= BuildConfig.HOST+"config/getServiceTerm";//服务条款

    public static final String Updata= BuildConfig.HOST+"config/getLatestVersion";//更新
}
