package self.xhl.com.common.ktextension

import android.app.Fragment
import android.content.Context
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author xhl
 * @version 1.0.0
 */
//将Rx Disposabel装入CompositeDisposable
fun Disposable.addTo(c: CompositeDisposable) {
    c.add(this)
}

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Fragment.toast(message: CharSequence) = activity.toast(message)

fun Context.longToast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Fragment.longToast(message: CharSequence) = activity.longToast(message)


