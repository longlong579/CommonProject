package self.xhl.com.common.utils

import android.widget.Toast
import self.xhl.com.common.app.BaseApp

/**
* @作者 xhl
* @des 防止多次显示toast
* @创建时间 2018/7/11 14:15
*/
class ToastUtil {
    companion object {
        var toast: Toast? = null

        fun shortToast(retId: Int) {
            if (toast == null) {
                toast = Toast.makeText(BaseApp.instance, retId, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(retId)
                toast!!.duration = Toast.LENGTH_SHORT
            }
            toast!!.show()
        }


        fun shortToast(hint: String) {
            if (toast == null) {
                toast = Toast.makeText(BaseApp.instance, hint, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(hint)
                toast!!.duration = Toast.LENGTH_SHORT
            }
            toast!!.show()
        }


        fun longToast(retId: Int) {
            if (toast == null) {
                toast = Toast.makeText(BaseApp.instance, retId, Toast.LENGTH_LONG)
            } else {
                toast!!.setText(retId)
                toast!!.duration = Toast.LENGTH_LONG
            }
            toast!!.show()
        }


        fun longToast(hint: String) {
            if (toast == null) {
                toast = Toast.makeText(BaseApp.instance, hint, Toast.LENGTH_LONG)
            } else {
                toast!!.setText(hint)
                toast!!.duration = Toast.LENGTH_LONG
            }
            toast!!.show()
        }
    }
}