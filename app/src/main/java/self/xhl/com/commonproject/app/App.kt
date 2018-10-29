package self.xhl.com.commonproject.app


import retrofit2.Retrofit
import self.xhl.com.common.app.BaseApp
import self.xhl.com.commonproject.BuildConfig
import self.xhl.com.net.makeRetrofit
import self.xhl.com.net.netParams.INetExternalParams
import self.xhl.com.net.netParams.NetworkIniter


class App : BaseApp()
{
    override fun onCreate() {
        super.onCreate()
//    Utils.init(applicationContext)//blankj:utilcode
//        Fragmentation.builder()
//                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
//                /**
//                 * 可以获取到[me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning]
//                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
//                 */
//                .handleException {
//                    // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
//                    // Bugtags.sendException(e);
//                }
//                .install()
    }

    override fun genNetworkClient(isLongTimeout: Boolean): Retrofit {
        return makeRetrofit(isLongTimeout)
        //return Retrofit.Builder().build()
    }

    override fun setupNetworkClient() {
        NetworkIniter.init(NetworkIniter.Builder(this)
                .externalParams(object :INetExternalParams
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
                        return UserInfoManager.token
                    }

                    override fun sign(): String? {
                        return ""
                    }
                })
                .extraHeaders(mapOf("token" to UserInfoManager.token,
                        "Content-Type" to "application/json",
                        "Accept" to "application/json"))

                .build())
    }
}