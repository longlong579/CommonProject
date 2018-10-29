package self.xhl.com.common.app

import android.app.Application
import retrofit2.Retrofit

/**
 *
 * @author bingo xhl
 * @version 1.0.0
 */
 abstract class BaseApp : Application() {

    val mRetrofit: Retrofit by lazy {
        genNetworkClient(false)
    }

    val mLongTimeRetrofit by lazy {
        genNetworkClient(true)
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        setupNetworkClient()

    }

    abstract fun genNetworkClient(isLongTimeout: Boolean): Retrofit

    abstract fun setupNetworkClient()

    companion object {
        lateinit var instance: BaseApp
    }

}