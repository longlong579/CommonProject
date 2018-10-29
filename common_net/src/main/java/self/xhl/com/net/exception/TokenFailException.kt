package self.xhl.com.net.exception

/**
 * @author xhl
 * @desc
 * 2018/10/29 12:29
 */
class TokenFailException(val code:Int,val detailMsg: String) : RuntimeException(detailMsg) {

}