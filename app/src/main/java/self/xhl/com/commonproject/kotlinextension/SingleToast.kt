package self.xhl.com.commonproject.kotlinextension

import android.widget.Toast
import self.xhl.com.net.app.BaseApp

/**
 * @author xhl
 * @desc
 * 2018/12/27 17:14
 */
private var mToast: Toast? = null
fun Any?.singleToast(message: CharSequence?) {
    when (mToast == null) {
        true -> {
            mToast = Toast.makeText(BaseApp.instance, message, Toast.LENGTH_SHORT)//利用全局Context 简化代码 否则每次Activity销毁都要置null
        }
        else -> {
            mToast?.setText(message)
            mToast?.duration = Toast.LENGTH_SHORT
        }
    }
    mToast?.show()
}