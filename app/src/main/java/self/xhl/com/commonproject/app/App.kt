package self.xhl.com.commonproject.app

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.multidex.MultiDex
import com.orhanobut.logger.*
import com.squareup.leakcanary.LeakCanary
import retrofit2.Retrofit
import self.xhl.com.commonproject.BuildConfig
import self.xhl.com.commonproject.StartServiceReceiver
import self.xhl.com.net.app.BaseApp
import self.xhl.com.net.makeRetrofit
import self.xhl.com.net.netParams.INetExternalParams
import self.xhl.com.net.netParams.NetworkIniter


/**
 * @author xhl
 * @desc
 * 2018/12/27 17:19
 */
class App : BaseApp() {
    override fun onCreate() {
        super.onCreate()
//        MobSDK.init(this)
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        MultiDex.install(this)

        //Logger 版本2.2.0
//        val formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            //    .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                // .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                // .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build()
//        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
//            override fun isLoggable(priority: Int, tag: String?): Boolean {
//                return BuildConfig.DEBUG
//            }
//        })
//        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Logger.init("mytag")    //LOG TAG默认是PRETTYLOGGER
                .methodCount(3)                 // 决定打印多少行（每一行代表一个方法）默认：2
                .hideThreadInfo()               // 隐藏线程信息 默认：显示
                .logLevel(LogLevel.FULL)        // 是否显示Log 默认：LogLevel.FULL（全部显示）
                .methodOffset(2)                // 默认：0



        //动态注册，此广播只能动态注册才能接收到
        var filter =  IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)//网络的连接（包括wifi和移动网络）
        registerReceiver(StartServiceReceiver(),filter)

    }

    override fun genNetworkClient(isLongTimeout: Boolean): Retrofit {
        return return makeRetrofit(isLongTimeout)
    }

    override fun setupNetworkClient() {
        NetworkIniter.init(NetworkIniter.Builder(this)
                .externalParams(object : INetExternalParams
                {
                    override fun isRelease(): Boolean {
                        return BuildConfig.IS_DEBUG
                    }

                    override fun httpHost(): String {
                        return BuildConfig.HOST
                    }

                    override fun httpsHost(): String? {
                        return BuildConfig.HOST
                    }

                    override fun getTokenKey(): String {
                        return "token"
                    }
                    override fun getToken(): String? {
                        return ""
                    }

                    override fun sign(): String? {
                        return ""
                    }
                })
                .build())
    }
}