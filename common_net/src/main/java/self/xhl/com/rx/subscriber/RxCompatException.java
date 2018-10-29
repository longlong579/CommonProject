package self.xhl.com.rx.subscriber;

/**
 * @author bingo
 * @version 1.0.0
 */

//不用改 将所有的异常转换为此异常类
public class RxCompatException extends RuntimeException {

    public static final int CODE_DEFAULT = 0x998833;
    public static final int CODE_SERVER_BUSY = 0x998832;
    public static final int CODE_NETWORK_TIMEOUT = 0x998833;
    public static final int CODE_NETWORK_UNAVAIABLE = 0x998834;
    public static final int CODE_TICK_OUT = 0x998888;

    public static final String ERROR_DEFAULT = "好像有点不对劲>_<!!";
    public static final String ERROR_SERVER_BUSY = "服务器繁忙>_<!!";
    public static final String ERROR_NETWORK_TIMEOUT = "请求超时!!";
    public static final String ERROR_NETWORK_UNAVAIABLE = "网络不可用，请检查网络连接!!";
    private int code;
    private String mOriginMsg;

    public RxCompatException() {
        this(CODE_DEFAULT, ERROR_DEFAULT, ERROR_DEFAULT);
    }

    public RxCompatException(Throwable cause, String originMsg) {
        this(CODE_DEFAULT, ERROR_DEFAULT, cause, originMsg);
    }

    public RxCompatException(int code, String message, Throwable cause, String originMsg) {
        super(message, cause);
        this.code = code;
        this.mOriginMsg = originMsg;
    }

    public RxCompatException(int code, String message, String originMsg) {
        super(message);
        this.code = code;
        this.mOriginMsg = originMsg;
    }

    public int getCode() {
        return code;
    }

    public String getOriginMsg() {
        return mOriginMsg;
    }
}
