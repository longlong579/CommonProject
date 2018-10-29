package self.xhl.com.net.netchgbypro.exception;

/**
 * @author xhl  根据实际项目修改
 * @version 1.0.0
 */
//外层Code不为0时 code的含义 根据实际项目设定
public class RespCode2Msg {
    public static final int LOGIN_VERIFY = 60010;//申请账号正在审核
    public static final int LOGIN_TICKOUT = 60013;//账号被提下线
    public static final int TOKEN_ERROR = -360;
    public static final int TOKEN_EXPIRE = -300;

}
