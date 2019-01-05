package self.xhl.com.common.ktutils

import android.widget.TextView
import com.blankj.utilcode.util.RegexUtils
import self.xhl.com.common.ktextension.content

/**
 * @author xhl
 * @desc
 * 2018/12/27 11:12
 */

//验证TextView/EditTextView  内容是否为空 不空为true
fun checkTextNotNull(view: TextView, errmsg:String):Boolean
{
    val boolean = view.content().isBlank()//全空
    if (boolean)
        view.setError(errmsg)
    return !boolean
}
//验证TextView/EditTextView  内容是否为空 不空为true
fun checkTextNotNull(view: TextView, errmsg:String, showError2TextView:Boolean, shouldReturnError:Boolean, returnError2:(errmsg:String)->Unit):Boolean
{
    val boolean = view.content().isBlank()//全空
    if (boolean) {
        if(showError2TextView)
            view.text=errmsg
        if (shouldReturnError)
            returnError2(errmsg)
    }
    return !boolean
}

//检验身份证
fun checkIDCard(view: TextView, errmsg:String):Boolean
{
    // val boolean =RegexUtils.isIDCard18Exact(view.text())
    val boolean =view.content().length==18
    if (!boolean)
        view.setError(errmsg)
    return boolean
}

//检验手机
fun checkTextIsPhone(view: TextView, errmsg:String):Boolean
{
    val boolean = RegexUtils.isMobileExact(view.content())
    if (!boolean)
        view.setError(errmsg)
    return boolean
}

//检验银行卡号 ？
fun checkBankNum(view: TextView, errmsg:String):Boolean
{
    val boolean =view.content().length<=19 && view.content().length>=16
    if (!boolean)
        view.setError(errmsg)
    return boolean
}