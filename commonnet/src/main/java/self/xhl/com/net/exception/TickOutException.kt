package self.xhl.com.net.exception

import java.lang.RuntimeException

/**
 *
 * @author bingo
 * @version 1.0.0 不改 账号踢出信息
 */
class TickOutException(val detailMsg: String) : RuntimeException(detailMsg) {

}