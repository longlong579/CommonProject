package self.xhl.com.commonproject.app

import retrofit2.Retrofit
import self.xhl.com.net.app.BaseApp
import self.xhl.com.net.makeRetrofit

/**
 * @author xhl
 * @desc
 * 2018/12/27 17:19
 */
class App: BaseApp()
{
    override fun onCreate() {
        super.onCreate()
    }

    override fun genNetworkClient(isLongTimeout: Boolean): Retrofit {
        return  return makeRetrofit(isLongTimeout)
    }

    override fun setupNetworkClient() {
    }
}