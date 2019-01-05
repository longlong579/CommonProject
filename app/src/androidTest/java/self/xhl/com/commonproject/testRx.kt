package self.xhl.com.commonproject

import android.support.test.runner.AndroidJUnit4
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author xhl
 * @desc
 * 2018/12/24 11:05
 */
@RunWith(AndroidJUnit4::class)
class testRx {
    @Test
    fun t() {
        val Tag = "RX: "
        val TagA = "RXSend: "
        var observer = Observable.create<String> {
            println(TagA + "A")
            it.onNext("A")

            println(TagA + "B")
            it.onNext("B")

//            println(TagA + "Throwable")
//            it.onError(Throwable())
//
//            println(TagA + "onComplete")
//            it.onComplete()

            println(TagA + "D")
            it.onNext("D")

            println(TagA + "E")
            it.onNext("E")
        }

        observer.subscribeOn(Schedulers.newThread())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onComplete() {
                        println(Tag + "onComplete")
                        println()
                    }

                    override fun onSubscribe(d: Disposable) {
                        println(Tag + "Disposable")
                    }

                    override fun onNext(t: String) {
                        println("After observeOn(Main)，Current thread is " + Thread.currentThread().getName())
                        println(Tag + t)
                        println()
                    }


                    override fun onError(e: Throwable) {
                        println(Tag + "onError")
                        println()
                    }
                })


//        observer.subscribe({
//            println("Result: "+it)
//        },{
//            println("RXError: "+it.toString())
//        },{
//            println("RXjava: "+"Completed")
//        })
    }

    @Test
    fun View.singclick()
    {
        Observable.create<View> {
            val emitter=it
            this.setOnClickListener {
                emitter.onNext(it)
            }
        }
                .subscribe {

                }
    }
}