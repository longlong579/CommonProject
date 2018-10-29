package self.xhl.com.net.netutil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @author bingo 不改
 * @version 1.0.0
 */

public class ExceptionUtil {
    /**
     * 是否连接服务器失败
     */
    public static boolean isServerBusy(Throwable throwable) {
        return (throwable instanceof SocketException) || (throwable instanceof IOException);
    }

    /**
     * 是否网络不可用
     *
     * @param throwable
     * @return
     */
    public static boolean isNetworkUnavailable(Throwable throwable) {
        return (throwable instanceof UnknownHostException) || (throwable instanceof ConnectException);
    }

    /**
     * 是否请求超时
     * @param throwable
     * @return
     */
    public static boolean isNetworkTimeout(Throwable throwable) {
        return (throwable instanceof SocketTimeoutException);
    }
}