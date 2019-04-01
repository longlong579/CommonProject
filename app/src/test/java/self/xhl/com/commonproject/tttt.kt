package self.xhl.com.commonproject

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test


/**
 * @author xhl
 * @desc
 * 2018/12/24 11:05
 */
class tttt {

    private fun initRxJava2() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setUp() {
        initRxJava2()
    }
    val Tag = "RX: "
    val TagA = "RXSend: "
    @Test
    fun t() {
        var observer = Observable.create<String> {
            //println("Current thread is " + Thread.currentThread().getName())
            println(TagA + "A")
            it.onNext("A")

            println(TagA + "B")
            it.onNext("B")

//            println(TagA + "Throwable")
//            it.onError(Throwable())



            println(TagA + "D")
            it.onNext("D")

            println(TagA + "E")
            it.onNext("E")

            println(TagA + "onComplete")
            it.onComplete()
        }

        observer.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext {
                    println("Current thread is " + Thread.currentThread().getName())
                    println("DoOnNext"+it)
                }
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
                        println("Current thread is " + Thread.currentThread().getName())
                        println(Tag + t)
                        println()
                    }


                    override fun onError(e: Throwable) {
                        println(Tag + "onError")
                        println()
                    }
                })
    }

    @Test
    fun ConcatTest()
    {
        val o1=Observable.create<Int> {
            it.apply {
                println(TagA+1)
                onNext(1)
                println(TagA+2)
                onNext(2)
                println(TagA+"onCompleted1")
                onError(Throwable("error1"))
                println(TagA+3)
                onNext(3)
                println(TagA+4)
                onNext(4)
            }
        }

        val o2=Observable.create<Int> {
            it.apply {
                println(TagA+11)
                onNext(11)
                println(TagA+22)
                onNext(22)
                println(TagA+"onCompleted2")
                onError(Throwable("error2"))
                onComplete()
                println(TagA+33)
                onNext(33)
                println(TagA+44)
                onNext(44)
            }
        }
        Observable.concat(o1,o2)
                .subscribe(object :Observer<Int>
                {
                    override fun onComplete() {
                        println("onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        println("onSubscribe")

                    }

                    override fun onNext(t: Int) {
                        println("onNext "+t)

                    }

                    override fun onError(e: Throwable) {
                        println("Throwable:  "+e.toString())

                    }
                })
    }

    @Test
    fun MergeTest()
    {
        val o1=Observable.create<Int> {
            it.apply {
                println(TagA+1)
                onNext(1)
                println(TagA+2)
                onNext(2)
//                println(TagA+"onCompleted1")
//                onError(Throwable("error1"))
                println(TagA+3)
                onNext(3)
                println(TagA+4)
                onNext(4)
            }
        }

        val o2=Observable.create<Int> {
            it.apply {
                println(TagA+11)
                onNext(11)
                println(TagA+22)
                onNext(22)
                println(TagA+"onCompleted2")
//                onError(Throwable("error2"))
//                onComplete()
                println(TagA+33)
                onNext(33)
                println(TagA+44)
                onNext(44)
            }
        }
        Observable.merge(o1,o2)
                .map {
                    it+100
                }.filter {
                    it>120
                }
                .flatMap {
                    Observable.just(1,3)
                }
                .subscribe(object :Observer<Int>
                {
                    override fun onComplete() {
                        println("onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        println("onSubscribe")

                    }

                    override fun onNext(t: Int) {
                        println("onNext "+t)

                    }

                    override fun onError(e: Throwable) {
                        println("Throwable:  "+e.toString())

                    }
                })
    }

    @Test
    fun ZipTest()
    {
        val o1=Observable.create<String>{
            it.apply {
                onNext("A")
                onNext("B")
            }
        }
        val o2=Observable.create<Int>{
            it.apply {
                onNext(1)
                onNext(2)
            }
        }

        Observable.zip(o1,o2,object :BiFunction<String,Int,String>
        {
            override fun apply(t1: String, t2: Int): String {
                return t1+t2
            }
        })
                .subscribe({
                    println(Tag+it)
                },{
                    println("Error")
                })

    }
}