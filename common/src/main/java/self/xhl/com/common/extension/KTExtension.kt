package self.xhl.com.common.extension

import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.blankj.utilcode.util.RegexUtils
import self.xhl.com.common.app.BaseApp
import java.io.File

/**
 *
 * @author xhl
 * @version 1.0.0
 */
fun Any.toast(msg: String?) {
    if (!msg.isNullOrBlank()) {
        Toast.makeText(BaseApp.instance, msg, Toast.LENGTH_SHORT).show()
    }
}

fun TextView.text():String
{
    return text.toString()
}

//验证TextView/EditTextView  内容是否为空 不空为true
fun checkTextNotNull(view:TextView,errmsg:String):Boolean
{
    val boolean = view.text().isBlank()//全空
    if (boolean)
        view.setError(errmsg)
    return !boolean
}
//验证TextView/EditTextView  内容是否为空 不空为true
fun checkTextNotNull(view: TextView, errmsg:String,showError2TextView:Boolean,shouldReturnError:Boolean,returnError2:(errmsg:String)->Unit):Boolean
{
    val boolean = view.text().isBlank()//全空
    if (boolean) {
        if(showError2TextView)
            view.text=errmsg
        if (shouldReturnError)
            returnError2(errmsg)
    }
    return !boolean
}

//检验身份证
fun checkIDCard(view:TextView,errmsg:String):Boolean
{
   // val boolean =RegexUtils.isIDCard18Exact(view.text())
    val boolean =view.text().length==18
    if (!boolean)
        view.setError(errmsg)
    return boolean
}

//检验手机
fun checkTextIsPhone(view:TextView,errmsg:String):Boolean
{
    val boolean =RegexUtils.isMobileExact(view.text())
    if (!boolean)
        view.setError(errmsg)
    return boolean
}

//检验银行卡号 ？
fun checkBankNum(view:TextView,errmsg:String):Boolean
{
    val boolean =view.text().length<=19 && view.text().length>=16
    if (!boolean)
        view.setError(errmsg)
    return boolean
}