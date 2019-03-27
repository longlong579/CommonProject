package self.xhl.com.commonproject.app

import com.mob.MobSDK
import com.orhanobut.logger.*
import com.squareup.leakcanary.LeakCanary
import retrofit2.Retrofit
import self.xhl.com.commonproject.BuildConfig
import self.xhl.com.net.app.BaseApp
import self.xhl.com.net.makeRetrofit


/**
 * @author xhl
 * @desc
 * 2018/12/27 17:19
 */
class App : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        MobSDK.init(this)
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        //Logger
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            //    .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                // .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                // .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
//        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
//            override fun isLoggable(priority: Int, tag: String?): Boolean {
//                return BuildConfig.DEBUG
//            }
//        })
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

    }

    override fun genNetworkClient(isLongTimeout: Boolean): Retrofit {
        return return makeRetrofit(isLongTimeout)
    }

    override fun setupNetworkClient() {
    }
}