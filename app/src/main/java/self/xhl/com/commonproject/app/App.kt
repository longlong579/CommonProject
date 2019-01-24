package self.xhl.com.commonproject.app

import com.squareup.leakcanary.LeakCanary
import retrofit2.Retrofit
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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    override fun genNetworkClient(isLongTimeout: Boolean): Retrofit {
        return return makeRetrofit(isLongTimeout)
    }

    override fun setupNetworkClient() {
    }
}