package self.xhl.com.net.netchgbypro.exception;

/**
 * @author bingo  根据实际项目修改
 * @version 1.0.0
 */
//外层Code不为0时 code的含义 yc项目这么规定而已
public class RespCode2Msg {
    public static final int LOGIN_VERIFY = 60010;//登录正在审核
    public static final int TOKEN_ERROR = -360;
    public static final int TOKEN_EXPIRE = -300;
    public static final int APPID_NOT_EXIST = -280;
    public static final int INTERNAL_SERVER_ERROR = -101;
    public static final int UNKNOWN_ERROR = -100;
    public static final int UNKNOWN_METHOD = -120;
    public static final int PARAMETER_ERROR = -140;
    public static final int SIGNATURE_ERROR = -180;

    public static final String MSG_TOKEN_ERROR = "token错误";
    public static final String MSG_TOKEN_EXPIRE = "token已过期";
    public static final String MSG_APPID_NOT_EXIST = "应用id不存在";
    public static final String MSG_INTERNAL_SERVER_ERROR = "服务器内部错误";
    public static final String MSG_UNKNOWN_ERROR = "服务端返回未知错误";
    public static final String MSG_UNKNOWN_METHOD = "mt参数服务端无法识别";
    public static final String MSG_PARAMETER_ERROR = "参数错误";
    public static final String MSG_SIGNATURE_ERROR = "签名错误";

}
