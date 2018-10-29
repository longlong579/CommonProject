package self.xhl.com.net.exception;

/**
 * @author bingo
 * @version 1.0.0
 * 错误码 不改
 */

public class RequestStatusEntity {
    private int code;
    private String msg;

    public RequestStatusEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
