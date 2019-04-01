package self.xhl.com.net.exception;

/**
 * @author bingo
 * @version 1.0.0
 */
//不改
public class BizException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final int mErrCode;

    public BizException(String Message, int errCode) {
        super(Message);
        mErrCode = errCode;
    }

    public int getErrCode() {
        return mErrCode;
    }
}
