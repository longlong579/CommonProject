package self.xhl.com.net.netchgbypro

import self.xhl.com.net.netutil.MD5Util

/**
 *
 * @author bingo
 * @version 1.0.0
 */
// 改 看是否需要 MD5参数校验 SIGN_KEY和服务端确定 规则由服务端 客户端约定
object SignHelper {
    private val SIGN_KEY = "djfasieandlafkd"

    fun genGetSign(mapParams: MutableMap<String, String>): String {
        // 排序
        val sortedMap = mapParams.toSortedMap()

        // k1=v1&k2=v2 拼接
        val strParams = sortedMap.toList().joinToString(separator = "&") {
            it.first + "=" + it.second
        }

        return innerSign(strParams)

    }

    fun genPostSign(bodyStr: String): String {
        return innerSign(bodyStr)
    }

    private fun innerSign(baseString: String): String {
        return MD5Util.genMD5Str(baseString.plus(SIGN_KEY)).toUpperCase()
    }

}