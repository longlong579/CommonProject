@file:JvmName("NetworkSetuper")
package self.xhl.com.net

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import self.xhl.com.net.netParams.NetworkIniter
import self.xhl.com.net.rxcalladapt.RxTransformErrorCallAdapterFactory
import self.xhl.com.net.netchgbypro.converter.YcRespTypeAdapterFactory
import self.xhl.com.net.netchgbypro.interceptor.ClipherFormEncodeInterceptor
import self.xhl.com.net.netchgbypro.interceptor.HeaderInterceptor
import java.util.concurrent.TimeUnit

/**
 *
 * @author bingo xhl
 * @version 1.0.0
 */
//创建retrofit
fun makeRetrofit(isLongTimeout: Boolean): Retrofit {
    val builder = Retrofit.Builder()

    val retrofit = builder
            .baseUrl(getHostUrl())
            .client(makeClient(isLongTimeout))
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .addCallAdapterFactory(RxTransformErrorCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .validateEagerly(false)
            .build()

    return retrofit
}

fun makeGson() = GsonBuilder()
        .registerTypeAdapterFactory(YcRespTypeAdapterFactory())
        .create()

fun makeClient(isLongTimeout: Boolean) = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(if (isLongTimeout) 60 else 30, TimeUnit.SECONDS)
        .addInterceptor(makeHeaderInterceptor())
       // .addInterceptor(makeClipherInterceptor())
        .build()

fun makeClipherInterceptor() = ClipherFormEncodeInterceptor()

fun makeHeaderInterceptor() = HeaderInterceptor()

fun getHostUrl(): String {
    return NetworkIniter.get().externalParams().httpHost()
}
