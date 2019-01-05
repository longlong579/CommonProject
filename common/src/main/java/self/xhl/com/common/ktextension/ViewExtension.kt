package self.xhl.com.common.ktextension

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * @author xhl
 * @desc  关于View的扩展方法
 * 2018/12/26 15:18
 */


//按钮防抖动
@SuppressLint("CheckResult")
@JvmOverloads
fun View.singclick(intervalDuration: Long = 1000,
                   unit: TimeUnit = TimeUnit.MILLISECONDS,
                   click: (v: View) -> Unit) {
    Observable.create<View> {
        val emitter = it
        this.setOnClickListener {
            v ->  emitter.onNext(v)

        }
    }
            .throttleFirst(intervalDuration, unit)
            .subscribe {
                click(it)
            }
}

//获取Text/EditText 的文本
fun TextView.content():String
{
    return text.toString()
}

//判断Text/EditText 的文本是否为空 ""或者多个空格（TextView的文本不可能为null）
fun TextView.isEmpty():Boolean
{
    if(content().isBlank())
        return true
    return false
}
