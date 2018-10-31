package self.xhl.com.commonproject.utils

import android.content.Context
import android.widget.Toast

/**
* @作者 xhl
* @des 防止多次显示toast
* @创建时间 2018/7/11 14:15
*/
class ToastUtil {
    companion object {
        var toast: Toast? = null

        fun shortToast(context:Context,retId: Int) {
            if (toast == null) {
                toast = Toast.makeText(context, retId, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(retId)
                toast!!.duration = Toast.LENGTH_SHORT
            }
            toast!!.show()
        }


        fun shortToast(context: Context,hint: String) {
            if (toast == null) {
                toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(hint)
                toast!!.duration = Toast.LENGTH_SHORT
            }
            toast!!.show()
        }


        fun longToast(context: Context,retId: Int) {
            if (toast == null) {
                toast = Toast.makeText(context, retId, Toast.LENGTH_LONG)
            } else {
                toast!!.setText(retId)
                toast!!.duration = Toast.LENGTH_LONG
            }
            toast!!.show()
        }


        fun longToast(context: Context,hint: String) {
            if (toast == null) {
                toast = Toast.makeText(context, hint, Toast.LENGTH_LONG)
            } else {
                toast!!.setText(hint)
                toast!!.duration = Toast.LENGTH_LONG
            }
            toast!!.show()
        }
    }
}