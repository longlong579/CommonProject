package self.xhl.com.common.widget

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.timmy.tdialog.TDialog
import self.xhl.com.common.R
import android.view.LayoutInflater


/**
 * @author xhl
 * @desc
 * 2018/7/27 11:23
 * build取消了setCancelAble，若需要 创建diaolog后自己调用
 */
class LoadingDialog {
    companion object {
        fun instance(fragmentManager: FragmentManager): TDialog {
            return TDialog.Builder(fragmentManager)
                    .setLayoutRes(R.layout.dialog_loading)
                    .setHeight(200)
                    .setWidth(200)
                    .setCancelableOutside(false)
                    .create()
        }

        fun instance(context: FragmentActivity, message: CharSequence?=null): TDialog {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.dialog_loading, null)
            val textView = view.findViewById<TextView>(R.id.tv_load_dialog)
            if(!message.isNullOrBlank())
                textView.text = message
            return TDialog.Builder(context.getSupportFragmentManager())
                    .setDialogView(view)
                    .setHeight(300)
                    .setWidth(300)
                    .setCancelableOutside(true)
                    .create()
        }
    }
}